package com.top_adv.myapplication1.FragmentView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.top_adv.myapplication1.R;

/**
 * Created by sst on 2016-10-18.
 * ViewPager Swipeable Screen disable
 * ontouchEvent(), onInterceptTouchEvent() setFalse
 */
public class NonSwipeViewPager extends ViewPager {

    private boolean swipeable;

    public NonSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyViewPager);
        try {
            swipeable = a.getBoolean(R.styleable.MyViewPager_swipeable, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable ? super.onTouchEvent(event) : false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.swipeable = enabled;
    }
}
