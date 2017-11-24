package com.myapp.adminside.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.myapp.adminside.R;
import com.myapp.adminside.custom.TfButton;
import com.myapp.adminside.custom.TfEditText;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.AdvancedSpannableString;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.helper.PrefUtils;
import com.myapp.adminside.model.User;

public class LoginActivity extends AppCompatActivity {

    private TfTextView txtTitle;
    private TfEditText edtEmailId;
    private TfEditText edtPassword;
    private TfTextView txtForgotPassword;
    private TfButton btnSignIn;
    private TfTextView txtSignUp;
    private CardView loginView;

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

                User user = PrefUtils.getUserProfileDetails(LoginActivity.this);
                if (user == null) {
                    Functions.showToast(LoginActivity.this, "Wrong credential");
                    return;
                } else {
                    if (user.getEmailId().equals(edtEmailId.getText().toString().trim()) && user.getPassword().equals(edtPassword.getText().toString().trim())) {
                        PrefUtils.setLoggedIn(LoginActivity.this, true);
                        Functions.fireIntent(LoginActivity.this, MainActivity.class, true);
                        finish();
                    } else {
                        Functions.showToast(LoginActivity.this, "Wrong credential");
                        return;
                    }
                }

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

    private void init() {
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
