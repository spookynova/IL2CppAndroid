package com.android.support;

import android.content.Context;
import android.content.Intent;

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
        Intent intent = new Intent(context, com.android.support.Launcher.class);
        intent.putExtra("overlayGranted",Utils.CheckOverlayPermissions(context));
        if(!Utils.CheckOverlayPermissions(context)){
            Utils.RequestOverlayPermissions(context);
        }
        context.startService(intent);
    }
}
