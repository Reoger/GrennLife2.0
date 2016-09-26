package com.reoger.grennlife.MainProject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.MainProject.model.MyViewHolder;
import com.reoger.grennlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/9/15.
 * 动态的list适配器
 */
public class DynamicAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context mContext;
    private List mDatas = new ArrayList<>();

    public DynamicAdapter(Context contex,List datas) {
        mContext = contex;
        mDatas= datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mItemText.setText(mDatas.get(position)+"sdhi");
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void  getData() {
        for (int i = 0; i < 10; i++) {
            mDatas.add("我就是最帅(on adapter)"+i);
        }

    }
}
