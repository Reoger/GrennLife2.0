package com.reoger.grennlife.MainProject.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
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

    public ImageView mDynamicShare;
    public ImageView mDynamicLike;
    public ImageView mDynamicComment;

    public GridLayout  mL;

    public MyViewHolder(View itemView) {
        super(itemView);
        mItemAuthor = (TextView) itemView.findViewById(R.id.dynamic_item_author);
        mItemContent = (TextView) itemView.findViewById(R.id.dynamic_item_content);
        mItemTimeAndLocation = (TextView) itemView.findViewById(R.id.dynamic_item_time_location);

        mDynamicShare = (ImageView) itemView.findViewById(R.id.dynamic_item_share);
        mDynamicLike = (ImageView) itemView.findViewById(R.id.dynamic_item_like);
        mDynamicComment = (ImageView) itemView.findViewById(R.id.dynamic_item_comments);

        mL = (GridLayout) itemView.findViewById(R.id.dynamic_gridlayout);

    }
}
