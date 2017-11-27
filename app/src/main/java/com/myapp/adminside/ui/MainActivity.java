package com.myapp.adminside.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myapp.adminside.R;
import com.myapp.adminside.adapter.SiteAdapter;
import com.myapp.adminside.api.AppApi;
import com.myapp.adminside.custom.TfButton;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.helper.MyApplication;
import com.myapp.adminside.helper.ProgressBarHelper;
import com.myapp.adminside.model.BaseResponse;
import com.myapp.adminside.model.Site;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        actionListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        callApi();
    }

    private void actionListener() {
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Functions.isConnected(MainActivity.this)) {
                    Functions.fireIntent(MainActivity.this, AddSiteActivity.class, true);
                } else {
                    Functions.showToast(MainActivity.this, "Please check your internet connection");
                }
            }
        });
    }

    private void init() {
        progressBar = new ProgressBarHelper(this, false);
        txtAlert = (TfTextView) findViewById(R.id.txtAlert);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtAdd = (TfTextView) findViewById(R.id.txtAdd);
        txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        initToolbar();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(15, 15, 15, 15);
            }
        });
        list = new ArrayList<>();

        adapter = new SiteAdapter(this, list);
        recyclerView.setAdapter(adapter);

        callApi();
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

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setTitle("");
    }
}
