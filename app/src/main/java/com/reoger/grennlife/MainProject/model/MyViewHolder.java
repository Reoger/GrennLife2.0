package com.reoger.grennlife.MainProject.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by 24540 on 2016/9/15.
 */
public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView mItemContent;
    public TextView mItemAuthor;
    public TextView mItemTimeAndLocation;

    public LinearLayout mDynamicShare;
    public LinearLayout mDynamicLike;
    public LinearLayout mDynamicComment;

    public MyViewHolder(View itemView) {
        super(itemView);
        mItemAuthor = (TextView) itemView.findViewById(R.id.dynamic_item_author);
        mItemContent = (TextView) itemView.findViewById(R.id.dynamic_item_content);
        mItemTimeAndLocation = (TextView) itemView.findViewById(R.id.dynamic_item_time_location);

        mDynamicShare = (LinearLayout) itemView.findViewById(R.id.dynamic_item_share);
        mDynamicLike = (LinearLayout) itemView.findViewById(R.id.dynamic_item_like);
        mDynamicComment = (LinearLayout) itemView.findViewById(R.id.dynamic_item_comments);

    }
}
