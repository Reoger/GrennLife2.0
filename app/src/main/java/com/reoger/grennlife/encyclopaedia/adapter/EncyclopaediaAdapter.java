package com.reoger.grennlife.encyclopaedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;

import java.util.ArrayList;

/**
 * Created by admin on 2016/9/18.
 */
public class EncyclopaediaAdapter extends RecyclerView.Adapter<EncyclopaediaViewHolder>{

    private Context mContext;
    private ArrayList<EncyclopaediaBean> mData;

    public EncyclopaediaAdapter(Context mContext, ArrayList<EncyclopaediaBean> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public EncyclopaediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_encyclopaedia,parent,false);
        EncyclopaediaViewHolder viewHolder = new EncyclopaediaViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EncyclopaediaViewHolder holder, int position) {
        holder.mContentTextView.setText(mData.get(position).getmContent());
        holder.mTitleTextView.setText(mData.get(position).getmTitle());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

