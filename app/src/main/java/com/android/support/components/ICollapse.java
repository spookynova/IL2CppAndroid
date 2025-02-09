package com.android.support.components;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.support.Menu;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class ICollapse {

    private Context context;
    private Typeface typeface;

    private LinearLayout mCollapseContent;

    public ICollapse(Context context, Typeface typeface) {
        this.context = context;
        this.typeface = typeface;
    }

    public LinearLayout getCollapseContent() {
        return mCollapseContent;
    }

    public void add(LinearLayout mContent, final String text, final boolean expanded) {
        LinearLayout.LayoutParams mLayoutParam = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        mLayoutParam.setMargins(0, 5, 0, 5);

        LinearLayout collapse = new LinearLayout(context);
        GradientDrawable collapseDrawable = new GradientDrawable();
        collapseDrawable.setCornerRadius(Menu.MENU_CORNER_RADIUS); //Set corner
        collapseDrawable.setColor(Colors.MENU_BG_COLOR); //Set background color

        collapse.setBackground(collapseDrawable); //Apply GradientDrawable to it
        collapse.setLayoutParams(mLayoutParam);
        collapse.setVerticalGravity(16);
        collapse.setOrientation(LinearLayout.VERTICAL);

        GradientDrawable collapseContentDrawable = new GradientDrawable();

        collapseContentDrawable.setCornerRadius(Menu.MENU_CORNER_RADIUS); //Set corner
        collapseContentDrawable.setColor(Colors.MENU_FEATURE_BG_COLOR); //Set backgroun
        collapseContentDrawable.setStroke(2, Colors.MENU_BG_COLOR); //Set border

        final LinearLayout collapseContent = new LinearLayout(context);
        collapseContent.setVerticalGravity(30);
        collapseContent.setPadding(0, 2, 0, 5);
        collapseContent.setOrientation(LinearLayout.VERTICAL);
        collapseContent.setBackground(collapseContentDrawable);
        collapseContent.setVisibility(View.GONE);

        mCollapseContent = collapseContent;

        final TextView textView = new TextView(context);
//        textView.setBackgroundColor(MENU_BG_COLOR);
        textView.setText(" ▶ " + text);
        textView.setGravity(Gravity.START);
        textView.setTextColor(Colors.TEXT_COLOR_2);
        textView.setTypeface(typeface, Typeface.BOLD);
        textView.setPadding(10, 20, 0, 20);


        if (expanded) {
            collapseContent.setVisibility(View.VISIBLE);
            textView.setText(" ▼ " + text);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            boolean isChecked = expanded;

            @Override
            public void onClick(View v) {

                boolean z = !isChecked;
                isChecked = z;
                if (z) {

                    collapseContent.setVisibility(View.VISIBLE);
                    textView.setText(" ▼ " + text);

                    return;
                }
                collapseContent.setVisibility(View.GONE);
                textView.setText(" ▶ " + text);

            }
        });
        collapse.addView(textView);
        collapse.addView(collapseContent);
        mContent.addView(collapse);
    }
}
