package com.reoger.grennlife.recyclerPlayView.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.reoger.grennlife.recyclerPlayView.gear.DataSetSubject;
import com.reoger.grennlife.recyclerPlayView.gear.DataSetSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/10/5.
 */
public class BannerViewPagerAdapter extends PagerAdapter implements DataSetSubject{
    private List<DataSetSubscriber> mSubscribers = new ArrayList<>();
    private List< ? extends View> mDataView;
    private ViewPager.OnPageChangeListener mOnPageListener;

    public BannerViewPagerAdapter(List<? extends View> mDataView, ViewPager.OnPageChangeListener mOnPageListener) {
        this.mDataView = mDataView;
        this.mOnPageListener = mOnPageListener;
    }

    //开发第一阶段用，以后要写listener
    public BannerViewPagerAdapter(List<? extends View> mDataView) {
        this.mDataView = mDataView;
    }

    @Override
    public int getCount() {
        return mDataView.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = mDataView.get(position);
        int i = position;
//        if (mOnPageListener != null) {
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnPageListener.
//                }
//            });
//        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        notifySubscriber();
    }

    @Override
    public void registerSubscriber(DataSetSubscriber subscriber) {
        mSubscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(DataSetSubscriber subscriber) {
        mSubscribers.remove(subscriber);
    }

    @Override
    public void notifySubscriber() {
        for(DataSetSubscriber subscriber : mSubscribers){
            subscriber.update(getCount());
        }
    }
}
