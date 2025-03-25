package com.android.support.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.support.Menu;
import com.android.support.Preferences;

import org.lsposed.lsparanoid.Obfuscate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Obfuscate
public class IRadioButton {

    private final Context context;
    private final Typeface typeface;

    public IRadioButton(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, String featName, final String list) {
        //Credit: LoraZalora
        final List<String> lists = new LinkedList<>(Arrays.asList(list.split(",")));

        final TextView textView = new TextView(context);
        textView.setText(featName + ":");
        textView.setTextColor(Colors.TEXT_COLOR_2);
        textView.setTypeface(typeface);

        final RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setPadding(10, 5, 10, 5);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        radioGroup.addView(textView);

        for (int i = 0; i < lists.size(); i++) {
            final RadioButton radioButton = new RadioButton(context);
            final String finalfeatName = featName, radioName = lists.get(i);
            View.OnClickListener first_radio_listener = new View.OnClickListener() {
                public void onClick(View v) {
                    textView.setText(Html.fromHtml(finalfeatName + ": <font color='" + Colors.NumberTxtColor + "'>" + radioName));
                    Preferences.changeFeatureInt(finalfeatName, featNum, radioGroup.indexOfChild(radioButton));
                }
            };
            System.out.println(lists.get(i));
            radioButton.setText(lists.get(i));
            radioButton.setTextColor(Color.LTGRAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                radioButton.setButtonTintList(ColorStateList.valueOf(Colors.RadioColor));
            radioButton.setOnClickListener(first_radio_listener);
            radioGroup.addView(radioButton);
        }

        int index = Preferences.loadPrefInt(featName, featNum);
        if (index > 0) { //Preventing it to get an index less than 1. below 1 = null = crash
            textView.setText(Html.fromHtml(featName + ": <font color='" + Colors.NumberTxtColor + "'>" + lists.get(index - 1)));
            ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
        }
        mContent.addView(radioGroup);
    }
}
