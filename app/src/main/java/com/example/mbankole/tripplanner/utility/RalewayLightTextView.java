package com.example.mbankole.tripplanner.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by danahe97 on 7/13/17.
 */

public class RalewayLightTextView extends TextView {

    public RalewayLightTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public RalewayLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public RalewayLightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Raleway-Medium.ttf", context);
        setTypeface(customFont);
    }
}