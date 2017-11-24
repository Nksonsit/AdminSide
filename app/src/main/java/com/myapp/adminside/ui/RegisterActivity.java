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


public class RegisterActivity extends AppCompatActivity {

    private TfTextView txtTitle;
    private TfEditText edtFirstName;
    private TfEditText edtLastName;
    private TfEditText edtEmailId;
    private TfEditText edtPassword;
    private TfEditText edtConfirmPassword;
    private TfButton btnSignUp;
    private TfTextView txtSignIn;
    private CardView loginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        actionListener();
    }

    private void actionListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.hideKeyPad(RegisterActivity.this, view);
                if (!Functions.isConnected(RegisterActivity.this)) {
                    Functions.showToast(RegisterActivity.this, getString(R.string.check_internet));
                    return;
                }
                if (edtFirstName.getText().toString().trim().length() == 0) {
                    Functions.showToast(RegisterActivity.this, "Please enter your first name");
                    return;
                }

                if (edtLastName.getText().toString().trim().length() == 0) {
                    Functions.showToast(RegisterActivity.this, "Please enter your last name");
                    return;
                }

                if (edtEmailId.getText().toString().trim().length() == 0) {
                    Functions.showToast(RegisterActivity.this, "Please enter your email id");
                    return;
                }

                if (!Functions.emailValidation(edtEmailId.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, "Please enter your valid email id");
                    return;
                }

                if (edtPassword.getText().toString().trim().length() == 0) {
                    Functions.showToast(RegisterActivity.this, "Please enter password");
                    return;
                }

                if (edtPassword.getText().toString().trim().length() < 4 || edtPassword.getText().toString().trim().length() > 16) {
                    Functions.showToast(RegisterActivity.this, "Password should be minimum 4 and maximum 16 character long");
                    return;
                }

                if (edtConfirmPassword.getText().toString().trim().length() == 0) {
                    Functions.showToast(RegisterActivity.this, "Please enter confirm password");
                    return;
                }

                if (!edtPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim())) {
                    Functions.showToast(RegisterActivity.this, "Password and confirm password should be same");
                    return;
                }

                User user = new User();
                user.setFirstName(edtFirstName.getText().toString().trim());
                user.setLastName(edtLastName.getText().toString().trim());
                user.setEmailId(edtEmailId.getText().toString().trim());
                user.setPassword(edtPassword.getText().toString().trim());

                PrefUtils.setUserFullProfileDetails(RegisterActivity.this, user);

                PrefUtils.setLoggedIn(RegisterActivity.this, true);
                Functions.fireIntent(RegisterActivity.this, MainActivity.class, true);
                finish();
            }
        });
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.hideKeyPad(RegisterActivity.this, view);
                Functions.fireIntent(RegisterActivity.this,LoginActivity.class, false);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Functions.fireIntent(RegisterActivity.this,LoginActivity.class, false);
        finish();
    }

    private void init() {
        loginView = (CardView) findViewById(R.id.loginView);
        txtSignIn = (TfTextView) findViewById(R.id.txtSignIn);
        btnSignUp = (TfButton) findViewById(R.id.btnSignUp);
        edtConfirmPassword = (TfEditText) findViewById(R.id.edtConfirmPassword);
        edtPassword = (TfEditText) findViewById(R.id.edtPassword);
        edtEmailId = (TfEditText) findViewById(R.id.edtEmailId);
        edtLastName = (TfEditText) findViewById(R.id.edtLastName);
        edtFirstName = (TfEditText) findViewById(R.id.edtFirstName);
        txtTitle = (TfTextView) findViewById(R.id.txtTitle);


        AdvancedSpannableString span = new AdvancedSpannableString("Welcome To Speed Test");
        span.setBold("Speed Test");
        span.setColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary), "Speed Test");
        txtTitle.setText(span);
    }
}
