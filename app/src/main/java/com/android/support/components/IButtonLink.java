package com.android.support.components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.support.Menu;

public class IButtonLink {

    private Context context;
    private Typeface typeface;
    public IButtonLink(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public void add(LinearLayout linLayout, final String featName, final String url) {

        LinearLayout.LayoutParams mLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLayoutParam.setMargins(0, 2, 0, 2);

        final Button button = new Button(context);
        button.setLayoutParams(mLayoutParam);
        button.setAllCaps(false); //Disable caps to support html
        button.setTextColor(Colors.TEXT_COLOR_2);
        button.setTypeface(typeface);
        button.setText(Html.fromHtml(featName));
        button.setBackgroundColor(Colors.BTN_COLOR);
        button.setPaintFlags(button.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);
        button.setOnClickListener(v -> {
            // Launch Url with chrome custom tab
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });

        linLayout.addView(button);
    }
}