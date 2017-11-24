package com.myapp.adminside.helper;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagartahelyani on 27-10-2017.
 */

public class MyApplication extends Application {

    public static Retrofit retrofit;
    public static Gson gson;
    public Context context;
    private static MyApplication mInstance;

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mInstance = this;
        initGson();
        //hashKey();
//        initRetrofit();

    }

    private void hashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.elections.gujarat",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void initRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void initGson() {
        gson = new GsonBuilder()
                .setLenient()
                .create();
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}

