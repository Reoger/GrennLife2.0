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
import com.reoger.grennlife.Recycle.adapter.MyAdapter;

import java.util.ArrayList;

import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/26.
 */
public class OldThingFrament extends Fragment{

    private ArrayList<String> mDatas = new ArrayList<>();
    private RefreshRecyclerView recyclerView;
    private MyAdapter myAdapter;

    private int counts = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.layout_base_main, container, false);
        initView();
        return messageLayout;
    }

    private void initView() {
        Context context = getContext();
        View header = View.inflate(context,R.layout.recycle_header2,null);
     //   View footer = View.inflate(context,R.layout.dynamic_botton,null);
        //recyclerView = (RefreshRecyclerView)getContext().findViewById(R.id.dynamic_recyclerView);
        recyclerView = (RefreshRecyclerView) getActivity().findViewById(R.id.dynamic_recyclerView);
        for (int i=0;i<10;i++){
            mDatas.add("reoger is the best handsome boy all the world！"+i);
        }
        myAdapter = new MyAdapter(context,mDatas);
        RecyclerViewManager.with(myAdapter,new LinearLayoutManager(context))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
          //      .addFooterView(footer)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        Message msg = new Message();
                        msg.what =10;
                        mHandler.sendMessageDelayed(msg,2000);
                    }

                    @Override
                    public void onLoadMore() {

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
                    mDatas.add(0, "new Item");
                    break;
                case 23:
                    for (int i = 0; i < 10; i++){
                        mDatas.add("item" + (counts + i));
                    }
                    counts += 10;
                    break;
            }
            recyclerView.onRefreshCompleted();
            myAdapter.notifyDataSetChanged();
        }
    };
}
