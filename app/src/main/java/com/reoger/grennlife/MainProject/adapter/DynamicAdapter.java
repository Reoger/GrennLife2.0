package com.reoger.grennlife.MainProject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.model.MyViewHolder;
import com.reoger.grennlife.MainProject.view.CommentActivity;
import com.reoger.grennlife.R;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/9/15.
 * 动态的list适配器
 */
public class DynamicAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context mContext;
    private List<Dynamic> mDatas = new ArrayList<>();

    public static final String COMMENTS = "comments";

    public DynamicAdapter(Context contex,List<Dynamic> datas) {
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mItemContent.setText(mDatas.get(position).getContent().toString());
      // holder.mItemAuthor.setText(mDatas.get(position).getAuthor().toString());
        // holder.mItemAuthor.setText(mDatas.get(position).getAuthor().toString());
        holder.mDynamicLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new toast(mContext,"点击了喜欢"+position);
            }
        });
        holder.mDynamicComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new toast(mContext,"评论ing");
                Bundle bundle = new Bundle();
                bundle.putSerializable(COMMENTS,mDatas.get(position));
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
