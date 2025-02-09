package com.android.support.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.android.support.Menu;
import com.android.support.Preferences;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class ISwitch {

    private Context context;
    private Typeface typeface;
    public ISwitch(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, final String featName, boolean swiOn) {

        LinearLayout.LayoutParams mLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLayoutParam.setMargins(0, 2, 0, 2);

        final Switch mSwitch = new Switch(context);
        mSwitch.setLayoutParams(mLayoutParam);
        ColorStateList buttonStates = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        Color.BLUE,
                        Colors.ToggleON, // ON
                        Colors.ToggleOFF // OFF
                }
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                mSwitch.getThumbDrawable().setTintList(buttonStates);
                mSwitch.getTrackDrawable().setTintList(buttonStates);
            } catch (NullPointerException ex) {
                Log.d("Mod_Menu", String.valueOf(ex));
            }
        }

        if (Preferences.loadPrefBool(featName, featNum, swiOn)) {
            mSwitch.setBackgroundColor(Color.parseColor("#40EC4857"));
        } else {
            mSwitch.setBackgroundColor(Color.parseColor("#00000000"));
        }

        mSwitch.setText(featName);
        mSwitch.setTextColor(Colors.TEXT_COLOR_2);
        mSwitch.setTypeface(typeface);
        mSwitch.setPadding(10, 5, 0, 5);
        mSwitch.setChecked(Preferences.loadPrefBool(featName, featNum, swiOn));
        mSwitch.setOnCheckedChangeListener((compoundButton, bool) -> {
            if (mSwitch.isChecked()) {
                Preferences.changeFeatureBool(featName, featNum, bool);
                mSwitch.setBackgroundColor(Color.parseColor("#40EC4857"));
            } else {
                Preferences.changeFeatureBool(featName, featNum, bool);
                mSwitch.setBackgroundColor(Color.parseColor("#00000000"));
            }
        });
        mContent.addView(mSwitch);
    }
}