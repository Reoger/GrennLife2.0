package com.reoger.grennlife.law.adapter;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.law.model.LawsBean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zimmerman on 2016/9/27.
 */
public class LawsViewAdapter extends RecyclerView.Adapter<LawsViewHolder> {
    private Context mContext;
    private ArrayList<BmobObject> datas;

    public LawsViewAdapter(Context mContext ,ArrayList<BmobObject> datas) {
        this.datas = datas;
        this.mContext = mContext;
    }


    @Override
    public LawsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_laws,parent,false);
        LawsViewHolder viewHolder = new LawsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LawsViewHolder holder, int position) {
        holder.mContentTextView.setText(
                (
                        (LawsBean)( datas.get(position) )
                ).getContent());
        holder.mTitleTextView.setText(
                (
                        (LawsBean)( datas.get(position) )
                ).getTitle());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
