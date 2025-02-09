package com.android.support.components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.support.Menu;
import com.android.support.Preferences;

import org.lsposed.lsparanoid.Obfuscate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
@Obfuscate
public class ISpinner {

    private Context context;
    private Typeface typeface;

    public ISpinner(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, final String featName, final String list) {
        final List<String> lists = new LinkedList<>(Arrays.asList(list.split(",")));

        LinearLayout.LayoutParams mLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParam.setMargins(0, 2, 0, 2);

        LinearLayout mLayoutContainer = new LinearLayout(context);
        mLayoutContainer.setOrientation(LinearLayout.VERTICAL);
        mLayoutContainer.setLayoutParams(mLayoutParam);

        // TextView
        final TextView textFeature = new TextView(context);
        textFeature.setText(featName + ":");
        textFeature.setTextColor(Colors.TEXT_COLOR_2);
        textFeature.setTypeface(typeface);
        textFeature.setPadding(10, 5, 0, 5);

        final Spinner spinner = new Spinner(context, Spinner.MODE_DROPDOWN);
        spinner.setLayoutParams(mLayoutParam);
        spinner.getBackground().setColorFilter(1, PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> aa = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, lists);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setSelection(Preferences.loadPrefInt(featName, featNum));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Preferences.changeFeatureInt(spinner.getSelectedItem().toString(), featNum, position);
                ((TextView) parentView.getChildAt(0)).setTextColor(Colors.TEXT_COLOR_2);
                ((TextView) parentView.getChildAt(0)).setTypeface(typeface);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mLayoutContainer.addView(textFeature);
        mLayoutContainer.addView(spinner);

        mContent.addView(mLayoutContainer);
    }
}