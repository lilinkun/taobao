package com.llk.mytaobao.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by LG on 2019/10/11.
 */
public class MotionViewPager extends ViewPager
{
    public MotionViewPager(Context paramContext)
    {
        super(paramContext);
    }

    public MotionViewPager(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}

