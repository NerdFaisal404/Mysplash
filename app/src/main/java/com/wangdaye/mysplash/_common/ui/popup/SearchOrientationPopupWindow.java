package com.wangdaye.mysplash._common.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wangdaye.mysplash.Mysplash;
import com.wangdaye.mysplash.R;
import com.wangdaye.mysplash._common.utils.ThemeUtils;
import com.wangdaye.mysplash._common.utils.TypefaceUtils;

/**
 * Search orientation popup window.
 * */

public class SearchOrientationPopupWindow extends PopupWindow
        implements View.OnClickListener {
    // widget
    private OnSearchOrientationChangedListener listener;

    // data
    private String[] names;
    private String[] values;
    private String valueNow;

    /** <br> life cycle. */

    public SearchOrientationPopupWindow(Context c, View anchor, String valueNow) {
        super(c);
        this.initialize(c, anchor, valueNow);
        Mysplash.getInstance().setActivityInBackstage(true);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Mysplash.getInstance().setActivityInBackstage(false);
            }
        });
    }

    @SuppressLint("InflateParams")
    private void initialize(Context c, View anchor, String valueNow) {
        View v = LayoutInflater.from(c).inflate(R.layout.popup_search_orientation, null);
        setContentView(v);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        initData(c, valueNow);
        initWidget();

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(10);
        }
        showAsDropDown(anchor, 0, 0, Gravity.CENTER);
    }

    /** <br> UI. */

    private void initWidget() {
        View v = getContentView();

        v.findViewById(R.id.popup_search_orientation_landscape).setOnClickListener(this);
        v.findViewById(R.id.popup_search_orientation_portrait).setOnClickListener(this);
        v.findViewById(R.id.popup_search_orientation_squarish).setOnClickListener(this);

        TextView landscapeTxt = (TextView) v.findViewById(R.id.popup_search_orientation_landscapeTxt);
        TypefaceUtils.setTypeface(v.getContext(), landscapeTxt);
        landscapeTxt.setText(names[0]);
        if (values[0].equals(valueNow)) {
            if (ThemeUtils.getInstance(v.getContext()).isLightTheme()) {
                landscapeTxt.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorTextSubtitle_light));
            } else {
                landscapeTxt.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorTextSubtitle_dark));
            }
        }

        TextView portraitTxt = (TextView) v.findViewById(R.id.popup_search_orientation_portraitTxt);
        TypefaceUtils.setTypeface(v.getContext(), portraitTxt);
        portraitTxt.setText(names[1]);
        if (values[1].equals(valueNow)) {
            if (ThemeUtils.getInstance(v.getContext()).isLightTheme()) {
                portraitTxt.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorTextSubtitle_light));
            } else {
                portraitTxt.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorTextSubtitle_dark));
            }
        }

        TextView squarishTxt = (TextView) v.findViewById(R.id.popup_search_orientation_squarishTxt);
        TypefaceUtils.setTypeface(v.getContext(), squarishTxt);
        squarishTxt.setText(names[2]);
        if (values[2].equals(valueNow)) {
            if (ThemeUtils.getInstance(v.getContext()).isLightTheme()) {
                squarishTxt.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorTextSubtitle_light));
            } else {
                squarishTxt.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorTextSubtitle_dark));
            }
        }

        if (ThemeUtils.getInstance(v.getContext()).isLightTheme()) {
            ((ImageView) v.findViewById(R.id.popup_search_orientation_landscapeIcon))
                    .setImageResource(R.drawable.ic_orientation_landscape_light);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_portraitIcon))
                    .setImageResource(R.drawable.ic_orientation_portrait_light);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_squarishIcon))
                    .setImageResource(R.drawable.ic_orientation_squarish_light);
        } else {
            ((ImageView) v.findViewById(R.id.popup_search_orientation_landscapeIcon))
                    .setImageResource(R.drawable.ic_orientation_landscape_dark);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_portraitIcon))
                    .setImageResource(R.drawable.ic_orientation_portrait_dark);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_squarishIcon))
                    .setImageResource(R.drawable.ic_orientation_squarish_dark);
        }
    }

    /** <br> data. */

    private void initData(Context c, String valueNow) {
        names = c.getResources().getStringArray(R.array.search_orientations);
        values = c.getResources().getStringArray(R.array.search_orientation_values);
        this.valueNow = valueNow;
    }

    /** <br> interface. */

    public interface OnSearchOrientationChangedListener {
        void onSearchOrientationChanged(String orientationValue);
    }

    public void setOnSearchOrientationChangedListener(OnSearchOrientationChangedListener l) {
        listener = l;
    }

    @Override
    public void onClick(View view) {
        String newValue = valueNow;
        switch (view.getId()) {
            case R.id.popup_search_orientation_landscape:
                newValue = values[0];
                break;

            case R.id.popup_search_orientation_portrait:
                newValue = values[1];
                break;

            case R.id.popup_search_orientation_squarish:
                newValue = values[2];
                break;
        }

        if (!newValue.equals(valueNow) && listener != null) {
            listener.onSearchOrientationChanged(newValue);
            dismiss();
        }
    }
}