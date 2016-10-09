package com.reoger.grennlife.technology.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.encyclopaedia.adapter.EncyclopaediaViewHolder;
import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;
import com.reoger.grennlife.technology.model.TechnologyBean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/9/28.
 */
public class TechnologyAdapter extends RecyclerView.Adapter<TechnologyViewHolder> {
    private Context mContext;
    private ArrayList<BmobObject> mData;

    public TechnologyAdapter(Context mContext, ArrayList<BmobObject> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<BmobObject> getmData() {
        return mData;
    }

    public void setmData(ArrayList<BmobObject> mData) {
        this.mData = mData;
    }

    @Override
    public TechnologyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_technology,parent,false);
        TechnologyViewHolder viewHolder = new TechnologyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TechnologyViewHolder holder, int position) {
        holder.mContentTextView.setText(
                (
                        (TechnologyBean)( mData.get(position) )
                ).getContent());
        holder.mTitleTextView.setText(
                (
                        (TechnologyBean)( mData.get(position) )
                ).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
