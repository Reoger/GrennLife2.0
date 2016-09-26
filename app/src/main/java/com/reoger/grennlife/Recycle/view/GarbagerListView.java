package com.reoger.grennlife.Recycle.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.adapter.Datas;
import com.reoger.grennlife.Recycle.adapter.MyAdapter;
import com.reoger.grennlife.Recycle.model.Garbager;

import java.util.ArrayList;
import java.util.List;

import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/19.
 */
public class GarbagerListView extends AppCompatActivity {
    private ArrayList<String> mDatas;
    private RefreshRecyclerView recyclerView;
    private MyAdapter myAdapter;
    private int counts = 10;

    private List<Garbager> datas;
    private Datas mdatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_garbager_main);
        mdatas = new Datas();

        datas = mdatas.getData();

        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("我是最帅的"+i);
        }
        View header = View.inflate(this,R.layout.recycle_header2,null);
        View footer = View.inflate(this,R.layout.dynamic_botton,null);
        recyclerView = (RefreshRecyclerView) findViewById(R.id.dynamic_recyclerView);
        myAdapter = new MyAdapter(this,mDatas);
        RecyclerViewManager.with(myAdapter,new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .addFooterView(footer)
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
                android.widget.Toast.makeText(GarbagerListView.this, "item" + position, android.widget.Toast.LENGTH_SHORT).show();
            }
        }).into(recyclerView,this);

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
