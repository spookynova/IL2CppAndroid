package com.android.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class Main {

    //Load lib
    static {
        // When you change the lib name, change also on Android.mk file
        // Both must have same name
        System.loadLibrary("GameHelper");
    }

    public static void Start(Context context) {
//        CrashHandler.init(context, false);
        Natives.LoadNativeLibPath(context.getApplicationInfo().nativeLibraryDir);
        Natives.CheckOverlayPermission(context);
    }
}
