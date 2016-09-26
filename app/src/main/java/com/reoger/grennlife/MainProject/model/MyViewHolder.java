package com.reoger.grennlife.MainProject.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by 24540 on 2016/9/15.
 */
public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView mItemText;

    public MyViewHolder(View itemView) {
        super(itemView);
        mItemText = (TextView) itemView.findViewById(R.id.dynamic_item_text);
    }
}
