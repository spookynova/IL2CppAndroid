package com.android.support;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

public abstract class Natives {

    public static native void Init(Context context, TextView title, TextView subTitle);

    public static native void CheckOverlayPermission(Context context);

    public static native String[] GetFeatureList();

    public static native boolean IsGameLibLoaded();

    public static native void LoadNativeLibPath(String path);

    public static native void OnDrawLoad(DrawView drawView, Canvas canvas);

    public static native byte[] LoadFontData(Context context);

}
