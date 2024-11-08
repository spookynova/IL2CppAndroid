package com.android.support.components;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.support.Menu;

public class ITextView {
    private Context context;
    private Typeface typeface;

    public ITextView(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout mContent, String text) {
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        textView.setTextColor(Colors.TEXT_COLOR_2);
        textView.setTypeface(typeface);
        textView.setPadding(10, 5, 10, 5);
        mContent.addView(textView);
    }
}
