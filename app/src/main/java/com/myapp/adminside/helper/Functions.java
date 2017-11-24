package com.myapp.adminside.helper;

/**
 * @author jatin
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.droidbyme.toastlib.ToastEnum;
import com.droidbyme.toastlib.ToastLib;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.myapp.adminside.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    private static final String ProfilePicture = "Elections/Profile";

    private static final SimpleDateFormat ServerDateTime = new SimpleDateFormat("MMM dd yyyy hh:mm a", Locale.US);
    private static final SimpleDateFormat AppDateTime = new SimpleDateFormat("dd MMM, yyyy hh:mm a", Locale.US);
    public static final SimpleDateFormat ChatDateTime = new SimpleDateFormat("MMM dd yyyy hh:mma", Locale.US);

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static void fireIntent(Context context, Class cls, boolean isNewActivity) {
        Intent i = new Intent(context, cls);
        context.startActivity(i);
        Activity activity = (Activity) context;
        if (!isNewActivity) {
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void  fireIntent(Context context, Intent intent, boolean isNewActivity) {
        Activity activity = (Activity) context;
        context.startActivity(intent);
        if (!isNewActivity) {
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void fireIntent(Activity context, boolean isNewActivity) {
        context.finish();
        if (!isNewActivity) {
            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void fireIntent(Activity context, Class<?> aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
        context.finish();
        context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void fireIntentForResult(Activity context, Class<?> cls, int requestCode, boolean isNewActivity) {

        Intent intent = new Intent(context, cls);
        context.startActivityForResult(intent, requestCode);
        if (!isNewActivity) {
            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    public static void fireIntentForResult(Activity context, Intent intent, int requestCode, boolean isNewActivity) {

        context.startActivityForResult(intent, requestCode);
        if (!isNewActivity) {
            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    public static void fireIntentSetResult(Activity context, int resultCode, boolean isNewActivity) {


        context.setResult(resultCode);
        context.finish();
        if (!isNewActivity) {
            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void fireIntentWithClearFlag(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void fireIntentWithClearFlagWithWithPendingTransition(Activity context, Class cls, boolean isNewActivity) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
        if (!isNewActivity) {
            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void fireIntentWithClearFlagWithWithPendingTransition(Activity context, Intent intent, boolean isNewActivity) {

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
        if (!isNewActivity) {
            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void fireIntentWithData(Context context, Intent intent, boolean isNewActivity) {
        context.startActivity(intent);
        Activity activity = (Activity) context;
        if (!isNewActivity) {
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String toStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static int toLength(EditText editText) {
        return editText.getText().toString().trim().length();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void openInMap(Context context, double latitude, double longitude, String labelName) {
        String newUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + labelName + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUri));
        context.startActivity(intent);
    }

    public static void makePhoneCall(Context context, String callNo) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + callNo));
        context.startActivity(dialIntent);
    }

    public static String parseDate(String inputDate) {

        Date date = null;
        String str = null;

        try {
            date = ServerDateTime.parse(inputDate);
            str = AppDateTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void share(Context context, String content) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(i, "Share"));
    }

    public static void showToast(Context context, String message) {
        new ToastLib.Builder(context, message)
                .duration(ToastEnum.SHORT)
                .backgroundColor(ContextCompat.getColor(context, R.color.button_bg))
                .textColor(ContextCompat.getColor(context, R.color.white))
                .typeface(getFontType(context, FontType.Regular.getId()))
                .textSize(16)
                .corner(20)
                .margin(128)
                .padding(20)
                .spacing(1)
                .gravity(Gravity.BOTTOM)
                .show();
    }

    public static Typeface getFontType(Context _context, int typeValue) {
        Typeface typeface = null;
        if (typeValue == 1) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");
        } else if (typeValue == 2) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/bold.ttf");
        } else {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");
        }
        return typeface;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return manufacturer + " " + model;
    }

/*    public static void showUserDetails(final Context context) {
        new DroidDialog.Builder(context)
                .title("User")
                .cancelable(true, true)
                .content(PrefUtils.getUserProfileDetails(context).toString())
                .show();
    }*/

    public static Spanned setHTML(String mainString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(mainString, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(mainString);
        }
    }

    public static String profilePicturePath() {

        return Environment.getExternalStorageDirectory() + File.separator + ProfilePicture + File.separator;
    }

    public static String jsonString(Object obj) {
        return "" + MyApplication.getGson().toJson(obj);
    }

    public static void logout(Context context) {
        /*PrefUtils.setLoggedIn(context, false);
        PrefUtils.setUserSkip(context, false);
        PrefUtils.setUserFullProfileDetails(context, new User());
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();*/

    }

    public static String getNotNullableString(String input) {
        if (input != null && input.trim().length() > 0) {
            return input.trim();
        } else {
            return "";
        }
    }


    public static void openPlayStore(Context context) {
        String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void setPermission(final Context context, @NonNull String[] permissions, PermissionListener permissionListene) {

        if (permissions.length == 0 && permissionListene != null) {
            return;
        }
        new TedPermission(context)
                .setPermissionListener(permissionListene)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(permissions)
                .check();
    }

    public static String getVersion(Context context) {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}
