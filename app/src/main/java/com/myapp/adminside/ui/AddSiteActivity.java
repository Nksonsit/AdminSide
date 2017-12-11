package com.myapp.adminside.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.gun0912.tedpermission.PermissionListener;
import com.myapp.adminside.R;
import com.myapp.adminside.api.AppApi;
import com.myapp.adminside.custom.TfButton;
import com.myapp.adminside.custom.TfEditText;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.helper.GPSTracker;
import com.myapp.adminside.helper.MyApplication;
import com.myapp.adminside.helper.PrefUtils;
import com.myapp.adminside.helper.ProgressBarHelper;
import com.myapp.adminside.model.BaseResponse;
import com.myapp.adminside.model.Site;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSiteActivity extends AppCompatActivity {

    private TfTextView txtTitle;
    private Toolbar toolbar;
    private TfEditText edtSite;
    private TfEditText edtDescription;
    private TfEditText edtDistance;
    private TfButton btnSubmit;
    private String TAG = "AddSiteActivity";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private double latitude = 0;
    private double longitude = 0;
    private ProgressBarHelper progressBar;
    private Site site;
    private TfEditText edtLongitude;
    private TfEditText edtLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Functions.setPermission(AddSiteActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        displayLocationSettingsRequest(AddSiteActivity.this);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Functions.showToast(AddSiteActivity.this, "You have denied service");
                        onBackPressed();
                    }
                });
            }
        }.start();


    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        init();
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(AddSiteActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            init();
        }
    }

    private void actionListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.hideKeyPad(AddSiteActivity.this, view);
                if (!Functions.isConnected(AddSiteActivity.this)) {
                    Functions.showToast(AddSiteActivity.this, "Please check your intrnet connection");
                    return;
                }
                if (edtSite.getText().toString().trim().length() == 0) {
                    Functions.showToast(AddSiteActivity.this, "Please enter site");
                    return;
                }

                if (edtDescription.getText().toString().trim().length() == 0) {
                    Functions.showToast(AddSiteActivity.this, "Please enter description");
                    return;
                }

                if (edtDistance.getText().toString().trim().length() == 0) {
                    Functions.showToast(AddSiteActivity.this, "Please enter distance");
                    return;
                }

                if (edtLatitude.getText().toString().trim().length() == 0) {
                    Functions.showToast(AddSiteActivity.this, "Please enter latitude");
                    return;
                }

                if (edtLongitude.getText().toString().trim().length() == 0) {
                    Functions.showToast(AddSiteActivity.this, "Please enter longitude");
                    return;
                }

                if (site == null) {
                    site = new Site();
                }

                site.setSite(edtSite.getText().toString().trim().replace("'", "&#39;"));
                site.setDescription(edtDescription.getText().toString().trim().replace("'", "&#39;"));
                site.setDistance(edtDistance.getText().toString().trim());
                site.setLatitude(edtLatitude.getText().toString().trim());
                site.setLongitude(edtLongitude.getText().toString().trim());
                site.setUserId(PrefUtils.getUserID(AddSiteActivity.this));
                site.setTimestamp(Functions.getTimestamp());

                callApi();
            }
        });
    }

    private void callApi() {
        progressBar.showProgressDialog();
        Log.e("add site req", MyApplication.getGson().toJson(site));
        AppApi api = MyApplication.getRetrofit().create(AppApi.class);
        api.addSite(site).enqueue(new Callback<BaseResponse<Site>>() {
            @Override
            public void onResponse(Call<BaseResponse<Site>> call, Response<BaseResponse<Site>> response) {
                progressBar.hideProgressDialog();
                if (response.body() != null && response.body().getStatus() == 1) {
                    Log.e("add site res", MyApplication.getGson().toJson(response.body()));
                    Functions.showToast(AddSiteActivity.this, "Successfully added");
                    onBackPressed();
                } else {
                    Functions.showToast(AddSiteActivity.this, getString(R.string.try_again));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Site>> call, Throwable t) {
                progressBar.hideProgressDialog();
                Functions.showToast(AddSiteActivity.this, getString(R.string.try_again));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Functions.fireIntent(AddSiteActivity.this, false);
    }

    private void init() {
        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.e("lat long", gpsTracker.getLatitude() + " " + gpsTracker.getLongitude());
        }

        progressBar = new ProgressBarHelper(this, false);
        btnSubmit = (TfButton) findViewById(R.id.btnSubmit);
        edtDistance = (TfEditText) findViewById(R.id.edtDistance);
        edtDescription = (TfEditText) findViewById(R.id.edtDescription);
        edtSite = (TfEditText) findViewById(R.id.edtSite);
        edtLongitude = (TfEditText) findViewById(R.id.edtLongitude);
        edtLatitude = (TfEditText) findViewById(R.id.edtLatitude);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        initToolbar();

        site = (Site) getIntent().getSerializableExtra("site");
        if (site != null) {
            edtSite.setText(site.getSite());
            edtDescription.setText(site.getDescription());
            edtDistance.setText(site.getDistance());
            btnSubmit.setText("UPDATE");

            try {
                if (site.getLatitude() != null && Double.parseDouble(site.getLatitude()) !=0 ) {
                    edtLatitude.setText(site.getLatitude());
                } else {
                    edtLatitude.setText(latitude + "");
                }
            } catch (Exception e) {
                edtLatitude.setText(latitude + "");
            }
            try {
                if (site.getLongitude() != null && Double.parseDouble(site.getLongitude()) != 0) {
                    edtLongitude.setText(site.getLongitude());
                } else {
                    edtLongitude.setText(longitude + "");
                }
            } catch (Exception e) {
                edtLongitude.setText(longitude + "");
            }
        }else{
            edtLatitude.setText(latitude + "");
            edtLongitude.setText(longitude + "");
        }
        actionListener();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
