package com.reoger.grennlife.recyclerPlayView.gear;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by admin on 2016/10/7.
 */
public class Indicator extends LinearLayout {
    private static final int DEFAULT_ITEM_COUNT = 5;
    private static final int DEFAULT_RADIUS = 10;
    private static final int DEFAULT_PADDING = 10;

    private View mMoveView;
    private Context mContext;
    private Paint mPaint;

    private int mRadius = DEFAULT_RADIUS;
    private int mPadding = DEFAULT_PADDING;
    private int mItemCount = DEFAULT_ITEM_COUNT;
    private int mCurrentItem = 0;
    private int mDistanceBtwItem = mRadius * 2 + mPadding;

    private float mOffset;



    public Indicator(Context context) {
        this(context,null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);

        mMoveView = new MoveView(mContext);
        addView(mMoveView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0 ;i<mItemCount;i++)
        {
            canvas.drawCircle(mPadding+mRadius+mRadius*2*i+mPadding*i,mPadding+mRadius
                    ,mRadius,mPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //小黄点的移动
        mMoveView.layout((int)(
                mPadding+mDistanceBtwItem*(mCurrentItem+mOffset) )
                ,mPadding,
                (int) (mDistanceBtwItem * ( 1 + mCurrentItem + mOffset) ),
                mPadding+mRadius*2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mPadding+(mRadius*2+mPadding)*mItemCount,(mPadding+mRadius)*2);
    }

    public void setPositionAndOffset(int position,float offset){
        this.mCurrentItem = position;
        this.mOffset  = offset;
        requestLayout();
    }

    public void setmItemCount(int mItemCount) {
        this.mItemCount = mItemCount;
        requestLayout();
    }

    public void setmPadding(int mPadding) {
        this.mPadding = mPadding;
        requestLayout();
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
        requestLayout();
    }

    private class MoveView extends View {
        private Paint mPaint;

        public MoveView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.argb(255,255,176,93));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(mRadius*2 ,mRadius*2);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
        }
    }

}
