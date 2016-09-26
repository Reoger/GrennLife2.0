package com.reoger.grennlife.Recycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.PublicHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/9/15.
 * 动态的list适配器
 */
public class MyAdapter extends RecyclerView.Adapter<PublicHolder>{
    private Context mContext;
    private List mDatas = new ArrayList<>();

    public MyAdapter(Context contex, List datas) {
        mContext = contex;
        mDatas= datas;
    }

    @Override
    public PublicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.public_item_recycle,parent,false);
        PublicHolder holder = new PublicHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PublicHolder holder, int position) {
        holder.mTitle.setText(mDatas.get(position)+"sdhi");
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}
