package com.myapp.adminside.fragment;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.myapp.adminside.R;
import com.myapp.adminside.adapter.StatsAdapter;
import com.myapp.adminside.api.AppApi;
import com.myapp.adminside.custom.TfButton;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.helper.MyApplication;
import com.myapp.adminside.helper.ProgressBarHelper;
import com.myapp.adminside.model.BaseResponse;
import com.myapp.adminside.model.Stats;
import com.myapp.adminside.model.StatsReq;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ishan on 29-11-2017.
 */

public class StatsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private View view;

    private TfTextView txtTitle;
    private Toolbar toolbar;
    private TfButton btnNewsHeadline;
    private TfButton btnLink;
    private TfButton btnRanking;
    private TfTextView txtAdd;
    private android.support.v7.widget.RecyclerView recyclerView;
    private TfTextView txtAlert;
    private List<Stats> list;
    private StatsAdapter adapter;
    private ProgressBarHelper progressBar;
    private TfTextView txtStartDate;
    private TfTextView txtEndDate;
    private Calendar now = Calendar.getInstance();
    private DatePickerDialog dpd;
    private boolean isStartData = false;
    private String startDate, endDate;
    private RelativeLayout topView;
    private ImageView imgRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_status, container, false);
        init();
        actionListener();
        return view;
    }

    private void actionListener() {
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartData = true;
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartData = false;
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(imgRefresh, "rotation", 0f, 360f);
                imageViewObjectAnimator.setDuration(500);
                imageViewObjectAnimator.start();
                if (Functions.isConnected(getActivity())) {
                    callApi();
                } else {
                    Functions.showToast(getActivity(), getActivity().getResources().getString(R.string.check_internet));
                }
            }
        });
    }

    private void init() {
        topView = (RelativeLayout) view.findViewById(R.id.topView);
        dpd = DatePickerDialog.newInstance(
                StatsFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        progressBar = new ProgressBarHelper(getActivity(), false);
        txtStartDate = (TfTextView) view.findViewById(R.id.txtStartDate);
        txtEndDate = (TfTextView) view.findViewById(R.id.txtEndDate);

        txtAlert = (TfTextView) view.findViewById(R.id.txtAlert);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        imgRefresh = (ImageView) view.findViewById(R.id.imgRefresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(15, 15, 15, 15);
            }
        });
        list = new ArrayList<>();

        adapter = new StatsAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            startDate =null;
            endDate=null;
            txtStartDate.setText("Start Date");
            txtEndDate.setText("End Date");
            if (Functions.isConnected(getActivity())) {
                callApi();
            } else {
                Functions.showToast(getActivity(), getActivity().getResources().getString(R.string.check_internet));
            }
        }
    }

    private void callApi() {
        progressBar.showProgressDialog();
        AppApi api = MyApplication.getRetrofit().create(AppApi.class);
        StatsReq statusReq = new StatsReq();
        if (startDate != null && startDate.trim().length() > 0) {
            statusReq.setStartDate(startDate);
        }
        if (endDate != null && endDate.trim().length() > 0) {
            statusReq.setEndDate(endDate);
        }
        Log.e("get status", MyApplication.getGson().toJson(statusReq));
        api.getStatus(statusReq).enqueue(new Callback<BaseResponse<Stats>>() {
            @Override
            public void onResponse(Call<BaseResponse<Stats>> call, Response<BaseResponse<Stats>> response) {
                progressBar.hideProgressDialog();
                if (response.body() != null && response.body().getStatus() == 1) {
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        txtAlert.setVisibility(View.GONE);
                        list = response.body().getData();
                        adapter.setDataList(list);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        txtAlert.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    txtAlert.setVisibility(View.VISIBLE);
//                    Functions.showToast(MainActivity.this, getString(R.string.try_again));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Stats>> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                txtAlert.setVisibility(View.VISIBLE);
                progressBar.hideProgressDialog();
//                Functions.showToast(MainActivity.this, getString(R.string.try_again));
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = (monthOfYear+1) + "";
        String day = dayOfMonth + "";
        if (month.trim().length() == 1) {
            month = "0" + month;
        }
        if (day.trim().length() == 1) {
            day = "0" + day;
        }
        if (isStartData) {
            startDate = year + "" + month + "" + day;
            txtStartDate.setText(day + " / " + month + " / " + year);
        } else {
            if(Integer.parseInt(startDate) <= Integer.parseInt(year + "" + month + "" + day) ){
            endDate = year + "" + month + "" + day;
            txtEndDate.setText(day + " / " + month + " / " + year);}else{
                Functions.showToast(getActivity(),"Please select valid date");
            }
        }
        isStartData = false;
    }
}
