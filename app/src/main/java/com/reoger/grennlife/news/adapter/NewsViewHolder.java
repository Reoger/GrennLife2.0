package com.reoger.grennlife.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by admin on 2016/9/26.
 */
public class NewsViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitleTextView;
    public TextView mContentTextView;
    public ImageView mImageView;
    public NewsViewHolder(View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView.findViewById(R.id.news_title_text);
        mContentTextView = (TextView) itemView.findViewById(R.id.news_content_text);
        mImageView = (ImageView) itemView.findViewById(R.id.news_image);
    }
}
