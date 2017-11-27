package com.myapp.adminside.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.myapp.adminside.R;
import com.myapp.adminside.api.AppApi;
import com.myapp.adminside.custom.TfButton;
import com.myapp.adminside.custom.TfEditText;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.AdvancedSpannableString;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.helper.MyApplication;
import com.myapp.adminside.helper.PrefUtils;
import com.myapp.adminside.helper.ProgressBarHelper;
import com.myapp.adminside.model.BaseResponse;
import com.myapp.adminside.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TfTextView txtTitle;
    private TfEditText edtEmailId;
    private TfEditText edtPassword;
    private TfTextView txtForgotPassword;
    private TfButton btnSignIn;
    private TfTextView txtSignUp;
    private CardView loginView;
    private ProgressBarHelper progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        actionListener();
    }

    private void actionListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.hideKeyPad(LoginActivity.this,view);
                if (!Functions.isConnected(LoginActivity.this)) {
                    Functions.showToast(LoginActivity.this, getString(R.string.check_internet));
                    return;
                }

                if (edtEmailId.getText().toString().trim().length() == 0) {
                    Functions.showToast(LoginActivity.this, "Please enter your email id");
                    return;
                }

                if (!Functions.emailValidation(edtEmailId.getText().toString().trim())) {
                    Functions.showToast(LoginActivity.this, "Please enter your valid email id");
                    return;
                }

                if (edtPassword.getText().toString().trim().length() == 0) {
                    Functions.showToast(LoginActivity.this, "Please enter password");
                    return;
                }

               callApi();

            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.hideKeyPad(LoginActivity.this,view);
                Functions.fireIntent(LoginActivity.this, RegisterActivity.class, true);
                finish();
            }
        });
    }

    private void callApi() {
        User user = new User();
        user.setPassword(edtPassword.getText().toString().trim());
        user.setEmailId(edtEmailId.getText().toString().trim());
        user.setType(2);
        progressBar.showProgressDialog();
        AppApi api = MyApplication.getRetrofit().create(AppApi.class);
        Log.e("login req", MyApplication.getGson().toJson(user));
        api.getLogin(user).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                progressBar.hideProgressDialog();
                if (response.body() != null && response.body().getStatus() == 1) {
                    Log.e("login res", MyApplication.getGson().toJson(response.body()));
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        PrefUtils.setLoggedIn(LoginActivity.this, true);
                        PrefUtils.setUserFullProfileDetails(LoginActivity.this, response.body().getData().get(0));
                        Functions.fireIntent(LoginActivity.this, MainActivity.class, true);
                        Functions.showToast(LoginActivity.this,"Successfully Login");
                        finish();
                    } else {
                        Functions.showToast(LoginActivity.this, "Wrong credential");
                    }
                } else {
                    Functions.showToast(LoginActivity.this, "Wrong credential");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                progressBar.hideProgressDialog();
                Functions.showToast(LoginActivity.this,"Something went wrong please try again later");
            }
        });

    }
    private void init() {
        progressBar = new ProgressBarHelper(this, false);
        loginView = (CardView) findViewById(R.id.loginView);
        txtSignUp = (TfTextView) findViewById(R.id.txtSignUp);
        btnSignIn = (TfButton) findViewById(R.id.btnSignIn);
        txtForgotPassword = (TfTextView) findViewById(R.id.txtForgotPassword);
        edtPassword = (TfEditText) findViewById(R.id.edtPassword);
        edtEmailId = (TfEditText) findViewById(R.id.edtEmailId);
        txtTitle = (TfTextView) findViewById(R.id.txtTitle);

        AdvancedSpannableString span = new AdvancedSpannableString("Welcome To Speed Test");
        span.setBold("Speed Test");
        span.setColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary), "Speed Test");
        txtTitle.setText(span);


    }
}
