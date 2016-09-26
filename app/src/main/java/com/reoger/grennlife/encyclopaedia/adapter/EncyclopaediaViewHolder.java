package com.reoger.grennlife.encyclopaedia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by admin on 2016/9/18.
 */
public class EncyclopaediaViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitleTextView;
    public TextView mContentTextView;
    public ImageView mImageView;
    public EncyclopaediaViewHolder(View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView.findViewById(R.id.encyclopaedia_title_text);
        mContentTextView = (TextView) itemView.findViewById(R.id.encyclopaedia_content_text);
        mImageView = (ImageView) itemView.findViewById(R.id.encyclopaedia_image);
    }
}
