package com.reoger.grennlife.technology.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by admin on 2016/9/28.
 */
public class TechnologyViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitleTextView;
    public TextView mContentTextView;
    public TechnologyViewHolder(View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView.findViewById(R.id.technology_title_text);
        mContentTextView = (TextView) itemView.findViewById(R.id.technology_content_text);

    }
}
