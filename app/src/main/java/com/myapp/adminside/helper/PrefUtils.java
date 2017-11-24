package com.myapp.adminside.helper;

import android.content.Context;

import com.myapp.adminside.model.User;


/**
 * Created by xitij on 17-03-2015.
 */
public class PrefUtils {

    public static String USER_ID = "UserId";
    public static String USER_PROFILE_KEY = "USER_PROFILE_KEY";
    public static String LOGGED_IN = "LOGGED_IN";
    public static String ENABLE_NOTIFICATION = "ENABLE_NOTIFICATION";
    public static String FCM = "FCM";

    public static void setNotification(Context ctx, boolean value) {
        Prefs.with(ctx).save(ENABLE_NOTIFICATION, value);
    }

    public static boolean isNotification(Context ctx) {
        return Prefs.with(ctx).getBoolean(ENABLE_NOTIFICATION, false);
    }

    public static void setLoggedIn(Context ctx, boolean value) {
        Prefs.with(ctx).save(LOGGED_IN, value);
    }

    public static boolean isUserLoggedIn(Context ctx) {
        return Prefs.with(ctx).getBoolean(LOGGED_IN, false);
    }

    public static void setUserID(Context ctx, int value) {
        Prefs.with(ctx).save(USER_ID, value);
    }

    public static int getUserID(Context ctx) {
        return Prefs.with(ctx).getInt(USER_ID, 0);
    }

    public static void setUserFullProfileDetails(Context context, User userProfile) {
        String toJson = MyApplication.getGson().toJson(userProfile);
        setUserID(context, userProfile.getUserId());
        Prefs.with(context).save(USER_PROFILE_KEY, toJson);
    }

    public static User getUserProfileDetails(Context context) {
        User userProfileDetails = null;
        String getUserProfileString = Prefs.with(context).getString(USER_PROFILE_KEY, "");
        try {
            userProfileDetails = MyApplication.getGson().fromJson(getUserProfileString, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userProfileDetails;
    }

    public static void setFCMToken(Context context, String token) {
        Prefs.with(context).save(FCM, token);
    }

    public static String getFCMToken(Context context) {
        return Prefs.with(context).getString(FCM, "");
    }
}
