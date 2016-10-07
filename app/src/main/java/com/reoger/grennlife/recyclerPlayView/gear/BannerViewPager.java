package com.reoger.grennlife.recyclerPlayView.gear;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reoger.grennlife.recyclerPlayView.adapter.BannerViewPagerAdapter;

/**
 * Created by admin on 2016/10/5.
 */
public class BannerViewPager extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    private Context mContext;
    private BannerViewPagerAdapter mAdapter;
    private Indicator mIndicator;
    private int mCurrentPosition;
    private int mViewPagerScrollState;

    private int mAutoRollingTime = 3000;
    private int mViewRealeseTime = 0;
    private static final int MESSAGE_AUTO_ROLLING = 0X1001;
    private static final int MESSAGE_AUTO_ROLLING_CANCEL = 0X1002;

    public void setmAutoRollingTime(int mAutoRollingTime) {
        this.mAutoRollingTime = mAutoRollingTime;
    }

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private Runnable mAutoRollingTask = new Runnable() {
        @Override
        public void run() {
            int present = (int) (System.currentTimeMillis()/1000);
            int timeDifference = mAutoRollingTime;

            if (mViewRealeseTime != 0) {
                timeDifference = (present - mViewRealeseTime)*1000;
            }
//            Log.d("qqe","timeDiff"+timeDifference+"  long:"+mAutoRollingTime*0.8);
            if (mViewPagerScrollState == ViewPager.SCROLL_STATE_IDLE) {
                if (timeDifference > mAutoRollingTime * 0.8) {
                    mHandler.sendEmptyMessage(MESSAGE_AUTO_ROLLING);
//                    Log.d("qqe", "run: big");
                } else {
                    mHandler.sendEmptyMessage(MESSAGE_AUTO_ROLLING_CANCEL);
//                    Log.d("qqe", "run: sss");

                }
            } else if (mViewPagerScrollState == ViewPager.SCROLL_STATE_DRAGGING) {
                mHandler.sendEmptyMessage(MESSAGE_AUTO_ROLLING_CANCEL);
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_AUTO_ROLLING:
                    if (mCurrentPosition == mAdapter.getCount() - 1) {
                        mPager.setCurrentItem(0, true);
                    } else {
                        mPager.setCurrentItem(mCurrentPosition+1, true);
                    }
                    postDelayed(mAutoRollingTask, mAutoRollingTime);
//                    Log.d("qqe", "in handleMessage");
                    break;
                case MESSAGE_AUTO_ROLLING_CANCEL:
                    postDelayed(mAutoRollingTask, mAutoRollingTime);
                    break;
            }
        }
    };

    private void initView() {
        //init view viewPager
        mPager = new ViewPager(mContext);
        ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = ViewPager.LayoutParams.MATCH_PARENT;
        lp.height = ViewPager.LayoutParams.MATCH_PARENT;
        mPager.setLayoutParams(lp);
        //init indicator
        mIndicator = new Indicator(mContext);
        LayoutParams indicatorLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        indicatorLp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        indicatorLp.bottomMargin = 20;
        mIndicator.setLayoutParams(indicatorLp);
    }

    public void setAdapter(BannerViewPagerAdapter adapter) {
        this.mAdapter = adapter;
        mPager.setAdapter(adapter);
        mPager.setOnPageChangeListener(this);

        mAdapter.registerSubscriber(new DataSetSubscriber() {
            @Override
            public void update(int count) {
                mIndicator.setmItemCount(count);
            }
        });
        addView(mPager);
        addView(mIndicator);

//        if(isAutoRolling){
        postDelayed(mAutoRollingTask, mAutoRollingTime);
//        }
    }

    private void setIndicator(int position,float offset){
        mIndicator.setPositionAndOffset(position,offset);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //这里可以处理指示器的变化
        setIndicator(position,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        this.mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            mViewPagerScrollState = ViewPager.SCROLL_STATE_DRAGGING;
        } else if (state == ViewPager.SCROLL_STATE_IDLE) {
            mViewPagerScrollState = ViewPager.SCROLL_STATE_IDLE;
            mViewRealeseTime = (int) (System.currentTimeMillis()/1000);
        }

    }
}
