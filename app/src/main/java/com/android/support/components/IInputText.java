package com.android.support.components;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.android.support.Menu;
import com.android.support.Preferences;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class IInputText {

    private Context context;
    private Typeface typeface;

    public IInputText(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, final int featNum, final String featName) {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams mLayoutParam = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
//        layoutParams.setMargins(7, 5, 7, 5);
        mLayoutParam.setMargins(0, 2, 0, 2);

        final Button button = new Button(context);

        String string = Preferences.loadPrefString(featName, featNum);
        button.setText(Html.fromHtml(featName + ": <font color='" + Colors.NumberTxtColor + "'>" + string + "</font>"));

        button.setAllCaps(false);
        button.setLayoutParams(mLayoutParam);
        button.setBackgroundColor(Colors.BTN_COLOR);
        button.setTextColor(Colors.TEXT_COLOR_2);
        button.setTypeface(typeface);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertName = new AlertDialog.Builder(context);

                final EditText editText = new EditText(context);
                editText.setOnFocusChangeListener((v, hasFocus) -> {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (hasFocus) {
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    } else {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                });
                editText.requestFocus();

                alertName.setTitle("Input text");
                alertName.setMessage("Enter text for " + featName);

                alertName.setView(editText);
                LinearLayout layoutName = new LinearLayout(context);
                layoutName.setOrientation(LinearLayout.VERTICAL);
                layoutName.addView(editText); // displays the user input bar
                alertName.setView(layoutName);

                alertName.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String str = editText.getText().toString();
                        button.setText(Html.fromHtml(featName + ": <font color='" + Colors.TEXT_COLOR_2 + "'>" + str + "</font>"));
                        Preferences.changeFeatureString(featName, featNum, str);
                        editText.setFocusable(false);
                    }
                });

                alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //dialog.cancel(); // closes dialog
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                });


                AlertDialog dialog = alertName.create(); // display the dialog
                dialog.getWindow().setType(Build.VERSION.SDK_INT >= 26 ? 2038 : 2002);
                dialog.show();
            }
        });

        linearLayout.addView(button);
        mContent.addView(linearLayout);
    }
}