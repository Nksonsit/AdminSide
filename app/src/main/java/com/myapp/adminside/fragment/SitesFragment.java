package com.myapp.adminside.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.adminside.R;
import com.myapp.adminside.adapter.SiteAdapter;
import com.myapp.adminside.api.AppApi;
import com.myapp.adminside.custom.TfButton;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.MyApplication;
import com.myapp.adminside.helper.ProgressBarHelper;
import com.myapp.adminside.model.BaseResponse;
import com.myapp.adminside.model.Site;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ishan on 29-11-2017.
 */

public class SitesFragment extends Fragment {
    private View view;

    private TfTextView txtTitle;
    private Toolbar toolbar;
    private TfButton btnNewsHeadline;
    private TfButton btnLink;
    private TfButton btnRanking;
    private TfTextView txtAdd;
    private android.support.v7.widget.RecyclerView recyclerView;
    private TfTextView txtAlert;
    private List<Site> list;
    private SiteAdapter adapter;
    private ProgressBarHelper progressBar;

    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sites,container,false);
        init();
        return view;
    }

    private void init() {
        progressBar = new ProgressBarHelper(getActivity(), false);
        txtAlert = (TfTextView) view.findViewById(R.id.txtAlert);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(15, 15, 15, 15);
            }
        });
        list = new ArrayList<>();

        adapter = new SiteAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

    }

    private void callApi() {
        progressBar.showProgressDialog();
        AppApi api = MyApplication.getRetrofit().create(AppApi.class);
        api.getSite().enqueue(new Callback<BaseResponse<Site>>() {
            @Override
            public void onResponse(Call<BaseResponse<Site>> call, Response<BaseResponse<Site>> response) {
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
            public void onFailure(Call<BaseResponse<Site>> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                txtAlert.setVisibility(View.VISIBLE);
                progressBar.hideProgressDialog();
//                Functions.showToast(MainActivity.this, getString(R.string.try_again));
            }
        });
    }

}
