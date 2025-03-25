package com.android.support.components;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.support.Menu;
import com.android.support.Preferences;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class IButton {

    private final Context context;
    private final Typeface typeface;

    public IButton(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, final String featName) {
        GradientDrawable buttonDrawable = new GradientDrawable();
        buttonDrawable.setCornerRadius(Menu.MENU_CORNER_RADIUS); //Set corner
        buttonDrawable.setColor(Colors.MENU_BG_COLOR); //Set background

        LinearLayout.LayoutParams mLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLayoutParam.setMargins(0, 2, 0, 2);

        final Button button = new Button(context);
        button.setLayoutParams(mLayoutParam);
        button.setTextColor(Colors.TEXT_COLOR_2);
        button.setTypeface(typeface);
        button.setAllCaps(false); //Disable caps to support html
        button.setText(Html.fromHtml(featName));
        button.setBackground(buttonDrawable);
        button.setOnClickListener(v -> Preferences.changeFeatureInt(featName, featNum, 0));

        mContent.addView(button);
    }
}