package com.reoger.grennlife.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.news.model.NewsBean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/9/26.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private Context mContext;
    private ArrayList<BmobObject> mDatas;

    public NewsAdapter(Context mContext, ArrayList<BmobObject> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        NewsViewHolder holder = new NewsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.mContentTextView.setText(
                (
                        (NewsBean)(mDatas.get(position) )
                ).getOutLine()
        );
        holder.mTitleTextView.setText(
                (
                        (NewsBean) (mDatas.get(position) )
                ).getTitle()
        );
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
