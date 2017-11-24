package com.myapp.adminside.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myapp.adminside.R;
import com.myapp.adminside.adapter.SiteAdapter;
import com.myapp.adminside.custom.TfButton;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.model.Site;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        actionListener();
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

        for (int i = 0; i < 10; i++) {
            Site site = new Site();
            site.setSite("Site " + (i + 1));
            site.setDistance("" + (1 + i));
            site.setDescription("Description");
            list.add(site);
        }

        adapter = new SiteAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setTitle("");
    }
}
