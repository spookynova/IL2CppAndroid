package com.android.support.components;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.support.Menu;
import com.android.support.Preferences;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class IInputInt {

    private Context context;
    private Typeface typeface;

    public IInputInt(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, final String featName, final int maxValue) {
        LinearLayout mLayoutContainer = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        int num = Preferences.loadPrefInt(featName, featNum);

        final Button button = new Button(context);
        button.setText(Html.fromHtml(featName + ": <font color='" + Colors.NumberTxtColor + "'>" + num + "</font>"));
        button.setAllCaps(false);
        button.setLayoutParams(layoutParams);
        button.setBackgroundColor(Colors.BTN_COLOR);
        button.setTextColor(Colors.TEXT_COLOR_2);
        button.setTypeface(typeface);
        button.setOnClickListener(view -> {
            AlertDialog.Builder alertName = new AlertDialog.Builder(context);
            final EditText editText = new EditText(context);
            if (maxValue != 0)
                editText.setHint("Max value: " + maxValue);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789-"));
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(10);
            editText.setFilters(FilterArray);
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (hasFocus) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            });
            editText.requestFocus();

            alertName.setTitle("Input number");
            alertName.setView(editText);
            LinearLayout layoutName = new LinearLayout(context);
            layoutName.setOrientation(LinearLayout.VERTICAL);
            layoutName.addView(editText); // displays the user input bar
            alertName.setView(layoutName);

            alertName.setPositiveButton("OK", (dialog, whichButton) -> {
                int num1;
                try {
                    String inp = editText.getText().toString();
                    num1 = Integer.parseInt(inp.isEmpty() ? "0" : inp);
                    if (maxValue != 0 && num1 >= maxValue)
                        num1 = maxValue;
                } catch (NumberFormatException ex) {
                    if (maxValue != 0)
                        num1 = maxValue;
                    else
                        num1 = Integer.MAX_VALUE;
                }

                button.setText(Html.fromHtml(featName + ": <font color='" + Colors.TEXT_COLOR_2 + "'>" + num1 + "</font>"));
                Preferences.changeFeatureInt(featName, featNum, num1);
                editText.setFocusable(false);
            });

            alertName.setNegativeButton("Cancel", (dialog, whichButton) -> {
                // dialog.cancel(); // closes dialog
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            });

            AlertDialog dialog = alertName.create(); // display the dialog
            dialog.getWindow().setType(Build.VERSION.SDK_INT >= 26 ? 2038 : 2002);
            dialog.show();
        });

        mLayoutContainer.addView(button);
        mContent.addView(mLayoutContainer);
    }
}