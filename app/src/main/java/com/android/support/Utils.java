package com.android.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.TypedValue;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class Utils {

    public static String getHwid(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void initRequest() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //For our image a little converter
    public static int convertDipToPixels(Context context,int i) {
        return Math.round(i * context.getResources().getDisplayMetrics().density);
    }

    public static int dp(Context context,int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) i, context.getResources().getDisplayMetrics());
    }

    public static boolean CheckOverlayPermissions(Context context){
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    public static void RequestOverlayPermissions(Context context){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
