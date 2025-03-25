package com.android.support.components;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.support.Menu;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class ICategory {

    private final Context context;
    private final Typeface typeface;

    public ICategory(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, String text) {

        GradientDrawable categoryDrawable = new GradientDrawable();
        categoryDrawable.setCornerRadius(Menu.MENU_CORNER_RADIUS); //Set corner
        categoryDrawable.setColor(Colors.MENU_BG_COLOR);

        TextView textView = new TextView(context);
        textView.setBackground(categoryDrawable);
        textView.setText(Html.fromHtml(text));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Colors.TEXT_COLOR_2);
        textView.setTypeface(typeface, Typeface.BOLD);
        textView.setPadding(0, 5, 0, 5);
        mContent.addView(textView);
    }
}
