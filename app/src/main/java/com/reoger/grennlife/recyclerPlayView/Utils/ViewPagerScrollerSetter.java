package com.reoger.grennlife.recyclerPlayView.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by admin on 2016/10/9.
 * 这个类是利用反射来设置轮播图切换时候的速度
 */
public class ViewPagerScrollerSetter extends Scroller {

    //滑动速度
    private int mScrollDuration = 3000;

    public ViewPagerScrollerSetter(Context context) {
        super(context);
    }

    public ViewPagerScrollerSetter(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerScrollerSetter(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setmScrollDuration(int mScrollDuration) {
        this.mScrollDuration = mScrollDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, duration);
    }

    public void initViewPagerScroll(ViewPager viewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//public class ViewPagerScroller extends Scroller {
//    private int mScrollDuration = 2000;             // 滑动速度
//
//    /**
//     * 设置速度速度
//     * @param duration
//     */
//    public void setScrollDuration(int duration){
//        this.mScrollDuration = duration;

//
//    @Override
//    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
//        super.startScroll(startX, startY, dx, dy, mScrollDuration);
//    }
//
//    @Override
//    public void startScroll(int startX, int startY, int dx, int dy) {
//        super.startScroll(startX, startY, dx, dy, mScrollDuration);
//    }
//
//
//
//    public void initViewPagerScroll(ViewPager viewPager) {
//        try {
//            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
//            mScroller.setAccessible(true);
//            mScroller.set(viewPager, this);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
