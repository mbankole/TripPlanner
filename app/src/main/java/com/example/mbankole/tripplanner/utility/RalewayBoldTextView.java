package com.example.mbankole.tripplanner.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by danahe97 on 7/13/17.
 */

public class RalewayBoldTextView extends TextView {

    public RalewayBoldTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public RalewayBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public RalewayBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Raleway-Bold.ttf", context);
        setTypeface(customFont);
    }
}