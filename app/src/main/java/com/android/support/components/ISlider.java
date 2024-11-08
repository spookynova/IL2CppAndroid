package com.android.support.components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.support.Menu;
import com.android.support.Preferences;

public class ISlider {

    private Context context;
    private Typeface typeface;

    public ISlider(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, final String featName, final int min, int max) {
        int progress = Preferences.loadPrefInt(featName, featNum);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setPadding(10, 5, 0, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        final TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(featName + ": <font color='" + Colors.NumberTxtColor + "'>" + ((progress == 0) ? min : progress)));
        textView.setTextColor(Colors.TEXT_COLOR_2);
        textView.setTypeface(typeface);

        SeekBar seekBar = new SeekBar(context);
        seekBar.setPadding(25, 10, 35, 10);
        seekBar.setMax(max);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            seekBar.setMin(min); //setMin for Oreo and above
        seekBar.setProgress((progress == 0) ? min : progress);
        seekBar.getThumb().setColorFilter(Colors.SliderColor, PorterDuff.Mode.SRC_ATOP);
        seekBar.getProgressDrawable().setColorFilter(Colors.SliderProgressColor, PorterDuff.Mode.SRC_ATOP);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                //if progress is greater than minimum, don't go below. Else, set progress
                seekBar.setProgress(Math.max(i, min));
                Preferences.changeFeatureInt(featName, featNum, Math.max(i, min));
                textView.setText(Html.fromHtml(featName + ": <font color='" + Colors.NumberTxtColor + "'>" + (i < min ? min : i)));
            }
        });
        linearLayout.addView(textView);
        linearLayout.addView(seekBar);

        mContent.addView(linearLayout);
    }
}