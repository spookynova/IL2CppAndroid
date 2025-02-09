package com.android.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.android.support.Utils.dp;

import com.android.support.components.Colors;
import com.android.support.components.IButton;
import com.android.support.components.IButtonLink;
import com.android.support.components.ICategory;
import com.android.support.components.ICheckBox;
import com.android.support.components.ICollapse;
import com.android.support.components.IInputInt;
import com.android.support.components.IInputText;
import com.android.support.components.IRadioButton;
import com.android.support.components.ISlider;
import com.android.support.components.ISpinner;
import com.android.support.components.ISwitch;
import com.android.support.components.ITextView;

import org.lsposed.lsparanoid.Obfuscate;

@Obfuscate
public class Menu {

    Typeface typeface;
    Context getContext;

    public static final String TAG = "R3DNetwork"; //Tag for logcat
    public static int MENU_WIDTH = 290;
    public static int POS_X = 0;
    public static int POS_Y = 100;
    public static float MENU_CORNER_RADIUS = 0f;
    boolean isExpanded = false;
    boolean overlayRequired;

    // ----------- Menu Layout ------------

    RelativeLayout __mCollapsed, __mRootContainer;
    LinearLayout __mExpanded, __featureContainer;
    LinearLayout.LayoutParams __menuExpanded;
    FrameLayout __frameLayout;
    ScrollView __scrollViewMenu;

    // ----------- Window Manager ------------
    WindowManager mWindowManager;
    WindowManager.LayoutParams vmParams;
    private WindowManager.LayoutParams canvasParams;

    // ----------- Components ------------
    DrawView drawView;
    ISwitch iSwitch;
    ISlider iSlider;
    IButton iButton;
    IInputText iInputText;
    IInputInt iInputInt;
    ICheckBox iCheckBox;
    IRadioButton iRadioButton;
    ISpinner iSpinner;
    IButtonLink iButtonLink;
    ICategory iCategory;
    ITextView iTextView;
    ICollapse iCollapse;

    //Here we write the code for our Menu
    // Reference: https://www.androidhive.info/2016/11/android-floating-widget-like-facebook-chat-head/
    public Menu(Context context) {
        getContext  = context;
        drawView    = new DrawView(getContext);
        typeface    = Typeface.DEFAULT;
        Preferences.context = context;
        InitComponent(context);
    }

    private void InitComponent(Context context) {
        // Set Components
        iSwitch     = new ISwitch(context, typeface);
        iSlider     = new ISlider(context, typeface);
        iButton     = new IButton(context, typeface);
        iInputText  = new IInputText(context, typeface);
        iInputInt   = new IInputInt(context, typeface);
        iCheckBox   = new ICheckBox(context, typeface);
        iRadioButton = new IRadioButton(context, typeface);
        iSpinner    = new ISpinner(context, typeface);
        iButtonLink = new IButtonLink(context, typeface);
        iCategory   = new ICategory(context, typeface);
        iTextView   = new ITextView(context, typeface);
        iCollapse   = new ICollapse(context, typeface);


        __frameLayout = new FrameLayout(context); // Global markup
        __frameLayout.setOnTouchListener(onTouchListener());
        __frameLayout.setAlpha(0.4f);

        __mRootContainer = new RelativeLayout(context); // Markup on which two markups of the icon and the menu itself will be placed
        __mCollapsed = new RelativeLayout(context); // Markup of the icon (when the menu is minimized)
        __mCollapsed.setVisibility(View.GONE);

        //********** The box of the mod menu **********
        __mExpanded = new LinearLayout(context); // Menu markup (when the menu is expanded)
        __mExpanded.setBackgroundColor(Colors.MENU_BG_COLOR);
        __mExpanded.setOrientation(LinearLayout.VERTICAL);
        __mExpanded.setVisibility(View.VISIBLE);
        __mExpanded.setPadding(1, 1, 1, 1); //So borders would be visible
        __mExpanded.setLayoutParams(new LinearLayout.LayoutParams(dp(getContext,MENU_WIDTH), WRAP_CONTENT));

        //********** The box of the mod menu **********
        GradientDrawable gdMenuBody = new GradientDrawable();
        gdMenuBody.setCornerRadius(MENU_CORNER_RADIUS); //Set corner
        gdMenuBody.setColor(Colors.MENU_BG_COLOR); //Set background color
//        gdMenuBody.setStroke(1, Color.parseColor("#32cb00")); //Set border
        __mExpanded.setBackground(gdMenuBody); //Apply GradientDrawable to it

        //********** Title **********
        RelativeLayout __titleLayout = new RelativeLayout(context);
        __titleLayout.setPadding(10, 5, 10, 5);
        __titleLayout.setVerticalGravity(16);
        __titleLayout.setGravity(Gravity.START);

        TextView _titleText = new TextView(context);
        _titleText.setTextColor(Colors.TEXT_COLOR);
        _titleText.setTextSize(18.0f);
        _titleText.setGravity(Gravity.START);
        _titleText.setTypeface(typeface);


        RelativeLayout.LayoutParams m_titleTextParam = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        m_titleTextParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        _titleText.setLayoutParams(m_titleTextParam);

        //********** Bottom Text **********
        TextView _bottomText = new TextView(context);
        _bottomText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        _bottomText.setMarqueeRepeatLimit(-1);
        _bottomText.setSingleLine(true);
        _bottomText.setSelected(true);
        _bottomText.setTextColor(Colors.TEXT_COLOR);
        _bottomText.setTypeface(typeface);
        _bottomText.setTextSize(12.0f);
        _bottomText.setGravity(Gravity.CENTER);
        _bottomText.setPadding(0, 15, 0, 15);
        _bottomText.setVisibility(View.GONE);


        //********** Minimize **********
        TextView _minimize = new TextView(context);
        _minimize.setText("▶ ");
        _minimize.setTextColor(Colors.TEXT_COLOR);
        _minimize.setTypeface(Typeface.DEFAULT_BOLD);
        _minimize.setTextSize(20.0f);
        _minimize.setPadding(10, 0, 0, 0);
        _minimize.setGravity(Gravity.CENTER_VERTICAL);
        _minimize.setOnClickListener(v -> {
            if (isExpanded) {
                isExpanded = false;
                __scrollViewMenu.setVisibility(View.GONE);
                __frameLayout.setAlpha(0.2f);

                _bottomText.setVisibility(View.GONE);
                _minimize.setText("▶ ");

            } else {
                isExpanded = true;
                __scrollViewMenu.setVisibility(View.VISIBLE);
                __frameLayout.setAlpha(1f);

                _bottomText.setVisibility(View.VISIBLE);
                _minimize.setText("▼ ");
            }
        });

        //********** Mod menu feature list **********
        __menuExpanded = new LinearLayout.LayoutParams(__mExpanded.getLayoutParams());
        __menuExpanded.weight = 1.0f;

        __scrollViewMenu = new ScrollView(context);
        __scrollViewMenu.setPadding(10, 10, 10, 10);
        __scrollViewMenu.setBackgroundColor(Colors.MENU_FEATURE_BG_COLOR);
        __scrollViewMenu.setLayoutParams(__menuExpanded);
        __scrollViewMenu.setVisibility(View.GONE);


        __featureContainer = new LinearLayout(context);
        __featureContainer.setOrientation(LinearLayout.VERTICAL);

        //********** Adding view components **********
        __mRootContainer.addView(__mExpanded);

        __titleLayout.addView(_minimize);
        __titleLayout.addView(_titleText);

        __mExpanded.addView(__titleLayout);
        __mExpanded.addView(__scrollViewMenu);
        __mExpanded.addView(_bottomText);

        __scrollViewMenu.addView(__featureContainer);

        Natives.Init(context, _titleText, _bottomText);
    }

    public void ShowMenu() {
        __frameLayout.addView(__mRootContainer);
        __featureContainer.removeAllViews();
        featureList(Natives.GetFeatureList(), __featureContainer);
    }

    @SuppressLint("WrongConstant")
    public void SetWindowManagerWindowService() {
        //Variable to check later if the phone supports Draw over other apps permission
        int mType = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? 2038 : 2002;
        vmParams = new WindowManager.LayoutParams(WRAP_CONTENT,WRAP_CONTENT, mType, 8, -3);
        //params = new WindowManager.LayoutParams(WindowManager.LayoutParams.LAST_APPLICATION_WINDOW, 8, -3);
        vmParams.gravity = 51;
        vmParams.x = POS_X;
        vmParams.y = POS_Y;

        mWindowManager = (WindowManager) getContext.getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                mType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
        );
        canvasParams = layoutParams;
        layoutParams.gravity = Gravity.TOP | Gravity.START;
        canvasParams.x = 0;
        canvasParams.y = 100;
        mWindowManager.addView(drawView, canvasParams);
        mWindowManager.addView(__frameLayout, vmParams);
        overlayRequired = true;
    }

    @SuppressLint("WrongConstant")
    public void SetWindowManagerActivity() {
        vmParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                POS_X,//initialX
                POS_Y,//initialy
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                PixelFormat.TRANSPARENT
        );
        vmParams.gravity = 51;
        vmParams.x = POS_X;
        vmParams.y = POS_Y;

        mWindowManager = ((Activity) getContext).getWindowManager();
        mWindowManager.addView(__frameLayout, vmParams);
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            final View collapsedView = __mCollapsed;
            final View expandedView = __mExpanded;
            private float initialTouchX, initialTouchY;
            private int initialX, initialY;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = vmParams.x;
                        initialY = vmParams.y;
                        initialTouchX = motionEvent.getRawX();
                        initialTouchY = motionEvent.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int rawX = (int) (motionEvent.getRawX() - initialTouchX);
                        int rawY = (int) (motionEvent.getRawY() - initialTouchY);
                        __mExpanded.setAlpha(1f);
                        __mCollapsed.setAlpha(1f);
                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (rawX < 10 && rawY < 10 && isViewCollapsed()) {
                            //When user clicks on the image view of the collapsed layout,
                            //visibility of the collapsed layout will be changed to "View.GONE"
                            //and expanded view will become visible.
                            try {
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        __mExpanded.setAlpha(0.3f);
                        __mCollapsed.setAlpha(0.3f);
                        //Calculate the X and Y coordinates of the view.
                        vmParams.x = initialX + ((int) (motionEvent.getRawX() - initialTouchX));
                        vmParams.y = initialY + ((int) (motionEvent.getRawY() - initialTouchY));
                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(__frameLayout, vmParams);
                        return true;
                    default:
                        return false;
                }
            }
        };
    }

    private void featureList(String[] features, LinearLayout linearLayout) {
        // App details
        iTextView.add(linearLayout, "App Package: " + getContext.getPackageName());
        // AppName
        iTextView.add(linearLayout, "App Name: " + getContext.getApplicationInfo().loadLabel(getContext.getPackageManager()));
        // App Version
        try {
            iTextView.add(linearLayout, "App Version: " + getContext.getPackageManager().getPackageInfo(getContext.getPackageName(), 0).versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int m_featureNumber, m_subFeature = 0;
        LinearLayout originalLayout = linearLayout;

        for (String item : features) {
            boolean switchedOn = item.contains("_True");
            String feature = switchedOn ? item.replaceFirst("_True", "") : item;

            // Restore original layout if necessary
            linearLayout = originalLayout;
            if (feature.contains("CollapseAdd_")) {
                linearLayout = iCollapse.getCollapseContent();
                feature = feature.replaceFirst("CollapseAdd_", "");
            }

            // Process feature number if present
            String[] featureParts = feature.split("_");
            if (TextUtils.isDigitsOnly(featureParts[0]) || featureParts[0].matches("-[0-9]*")) {
                m_featureNumber = Integer.parseInt(featureParts[0]);
                feature = feature.replaceFirst(featureParts[0] + "_", "");
                m_subFeature++;
            } else {
                m_featureNumber = features.length - m_subFeature;
            }

            // Handle feature addition
            addFeatureToLayout(linearLayout, m_featureNumber, feature.split("_"), switchedOn);
        }
    }

    // Method to add each feature based on its type
    // featureParts[0] is the type of feature like ISwitch, IButton, etc.
    // featureParts[1] is the name of the feature
    // featureParts[2] is the value of the feature
    // featureParts[3] is the max value of the feature
    private void addFeatureToLayout(LinearLayout layout, int featNum, String[] featureParts, boolean switchedOn) {
        switch (featureParts[0]) {
            case "ISwitch":
                iSwitch.add(layout, featNum, featureParts[1], switchedOn);
                break;
            case "ISlider":
                iSlider.add(layout, featNum, featureParts[1], Integer.parseInt(featureParts[2]), Integer.parseInt(featureParts[3]));
                break;
            case "IButton":
                iButton.add(layout, featNum, featureParts[1]);
                break;
            case "ISpinner":
                iSpinner.add(layout, featNum, featureParts[1], featureParts[2]);
                break;
            case "IInputText":
                iInputText.add(layout, featNum, featureParts[1]);
                break;
            case "IInputInt":
                iInputInt.add(layout, featNum, featureParts.length == 3 ? featureParts[2] : featureParts[1], featureParts.length == 3 ? Integer.parseInt(featureParts[1]) : 0);
                break;
            case "ICheckBox":
                iCheckBox.add(layout, featNum, featureParts[1], switchedOn);
                break;
            case "IRadioButton":
                iRadioButton.add(layout, featNum, featureParts[1], featureParts[2]);
                break;
            case "ICollapse":
                iCollapse.add(layout, featureParts[1], switchedOn);
                break;
            case "IButtonLink":
                iButtonLink.add(layout, featureParts[1], featureParts[2]);
                break;
            case "ICategory":
                iCategory.add(layout, featureParts[1]);
                break;
            case "ITextView":
                iTextView.add(layout, featureParts[1]);
                break;
            default:
                Log.e(TAG, "Feature not found: " + Arrays.toString(featureParts));
        }
    }

    private boolean isViewCollapsed() {
        return __frameLayout == null || __mCollapsed.getVisibility() == View.VISIBLE;
    }

    public void setVisibility(int view) {
        if (__frameLayout != null) {
            __frameLayout.setVisibility(view);
        }
    }

    public void onDestroy() {
        if (__frameLayout != null) {
            mWindowManager.removeView(__frameLayout);
        }

        if (drawView != null) {
            mWindowManager.removeView(drawView);
        }
    }

    // onPaused
    public void hideDrawView() {
        if (drawView != null) {
            drawView.setVisibility(View.GONE);
        }
    }

    public void showDrawView() {
        drawView.setVisibility(View.VISIBLE);
    }
}
