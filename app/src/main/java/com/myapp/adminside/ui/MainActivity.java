package com.myapp.adminside.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.myapp.adminside.R;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.fragment.SitesFragment;
import com.myapp.adminside.fragment.StutsFragment;
import com.myapp.adminside.helper.Functions;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private Context context;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private SitesFragment siteFragment;
    private TfTextView txtTitle;
    private TfTextView txtAdd;
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager container;
    private AppBarLayout appbar;
    private StutsFragment stutsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtAdd = (TfTextView) findViewById(R.id.txtAdd);
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


        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        highLightCurrentTab(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                highLightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initToolbar();
    }


    private void highLightCurrentTab(int position) {

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }

        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));

    }


    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        siteFragment = new SitesFragment();
        stutsFragment = new StutsFragment();
        adapter.addFragment(siteFragment, "Sites");
        adapter.addFragment(stutsFragment, "Stuts");

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(6);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        private View getTabView(int position) {
            TfTextView tfTextView = new TfTextView(context);
            tfTextView.setText(mFragmentTitleList.get(position));
            tfTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tfTextView.setTypeface(Functions.getFontType(context, 1));
            tfTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
            tfTextView.setGravity(Gravity.CENTER);
            return tfTextView;
        }

        private View getSelectedTabView(int position) {
            TfTextView tfTextView = new TfTextView(context);
            tfTextView.setText(mFragmentTitleList.get(position));
            tfTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tfTextView.setTypeface(Functions.getFontType(context, 2));
            tfTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
            tfTextView.setGravity(Gravity.CENTER);
            return tfTextView;
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setTitle("");
    }
}
