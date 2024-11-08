package com.android.support.components;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.support.Preferences;

public class ICheckBox {

    private Context context;
    private Typeface typeface;

    public ICheckBox(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, final String featName, boolean switchedOn) {
        LinearLayout mLayoutContainer = new LinearLayout(context);
        mLayoutContainer.setOrientation(LinearLayout.HORIZONTAL);

        final CheckBox checkBox = new CheckBox(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.setMargins(0, 2, 5, 2);
        checkBox.setLayoutParams(layoutParams);
        checkBox.setTextColor(Colors.TEXT_COLOR_2);

        // Text
        final TextView textView = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(textParams);
        textView.setText(featName);
        textView.setTextColor(Colors.TEXT_COLOR_2);
        textView.setTypeface(typeface);
        textView.setPadding(10, 5, 0, 5);

        mLayoutContainer.addView(textView);
        mLayoutContainer.addView(checkBox);

        if (Preferences.loadPrefBool(featName, featNum, switchedOn)) {
            mLayoutContainer.setBackgroundColor(Color.parseColor("#408C80FF"));
        } else {
            mLayoutContainer.setBackgroundColor(Color.parseColor("#00000000"));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            checkBox.setButtonTintList(ColorStateList.valueOf(Colors.CheckBoxColor));
        checkBox.setChecked(Preferences.loadPrefBool(featName, featNum, switchedOn));
//        checkBox.setBackgroundColor(Colors.OtherBG);
        checkBox.setOnCheckedChangeListener((v, isChecked) -> {
            if (checkBox.isChecked()) {
                Preferences.changeFeatureBool(featName, featNum, isChecked);
                mLayoutContainer.setBackgroundColor(Color.parseColor("#408C80FF"));
            } else {
                Preferences.changeFeatureBool(featName, featNum, isChecked);
                mLayoutContainer.setBackgroundColor(Color.parseColor("#00000000"));
            }
        });
        mContent.addView(mLayoutContainer);
    }
}
