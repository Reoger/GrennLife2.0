package com.reoger.grennlife.law.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by Zimmerman on 2016/9/27.
 */
public class LawsViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitleTextView;
    public TextView mContentTextView;
    public LawsViewHolder(View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView.findViewById(R.id.laws_title_text);
        mContentTextView = (TextView) itemView.findViewById(R.id.laws_content_text);
    }
}
