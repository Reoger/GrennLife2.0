package com.reoger.grennlife.Recycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.OldThing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/9/15.
 * 动态的list适配器
 */
public class OldThingsAdapter extends RecyclerView.Adapter<OldThingsAdapter.OldThingHolder>{
    private Context mContext;
    private List<OldThing> mDatas = new ArrayList<>();


    public OldThingsAdapter(Context contex, List<OldThing> datas) {
        mContext = contex;
        mDatas= datas;
    }

    @Override
    public OldThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic,parent,false);
        OldThingHolder holder = new OldThingHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(OldThingHolder holder, int position) {
        OldThing oldThing = mDatas.get(position);
        //设置
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class OldThingHolder extends RecyclerView.ViewHolder{


        public OldThingHolder(View itemView) {
            super(itemView);
        }
    }

}
