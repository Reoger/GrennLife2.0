package com.reoger.grennlife.Recycle.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.adapter.OldThingsAdapter;
import com.reoger.grennlife.Recycle.model.OldThing;

import java.util.ArrayList;
import java.util.List;

import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/26.
 */
public class OldThingFragment extends Fragment{

    private List<OldThing> mData = new ArrayList<OldThing>();
    private RefreshRecyclerView recyclerView;
    private OldThingsAdapter mOldthingAdapter;
    private View rootView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.layout_base_main_recycle2, container, false);
        rootView=messageLayout;
        initView();
        return messageLayout;

    }

    private void initView() {
        Context context = getContext();
        View header = View.inflate(context,R.layout.recycle_header2_tete,null);
        recyclerView = (RefreshRecyclerView) rootView.findViewById(R.id.dynamic_recyclerView2);
        //需要先初始化数据
        mOldthingAdapter = new OldThingsAdapter(context,mData);
        RecyclerViewManager.with(mOldthingAdapter,new LinearLayoutManager(context))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        //这里添加下拉刷刷新的逻辑
                    }

                    @Override
                    public void onLoadMore() {
                        //这里添加上拉加载更多的逻辑
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                android.widget.Toast.makeText(getContext(), "item" + position, android.widget.Toast.LENGTH_SHORT).show();
            }
        }).into(recyclerView,context);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    break;
                case 23:
                    break;
            }
            recyclerView.onRefreshCompleted();
            mOldthingAdapter.notifyDataSetChanged();
        }
    };
}
