package com.reoger.grennlife.Recycle.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by 24540 on 2016/9/20.
 */
public class PublicHolder extends RecyclerView.ViewHolder{
    public TextView mTitle;
    public TextView mContent;

    public PublicHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) (itemView).findViewById(R.id.public_title);
    }
}
