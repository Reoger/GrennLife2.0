package com.reoger.grennlife.news.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.news.adapter.NewsAdapter;
import com.reoger.grennlife.utils.CustomApplication;
import com.reoger.grennlife.utils.ServerDataOperation.IServerData;
import com.reoger.grennlife.utils.ServerDataOperation.ServerDataCompl;
import com.reoger.grennlife.utils.db.DBOperationCompl;
import com.reoger.grennlife.utils.db.IDBOperation;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by admin on 2016/9/26.
 */
public class NewsView extends Activity implements INewsView {
    private RefreshRecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<BmobObject> mDatas;

    private IDBOperation mDBOperation;
    private IServerData mServerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_main);
        initView();
        initAttr();
        recycleMethod();

    }

    private void initView() {
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.dynamic_recyclerView);
    }

    private void initAttr() {
        mServerData = new ServerDataCompl();
        mDBOperation = DBOperationCompl.getInstance(this, ServerDataCompl.BEAN_TYPE_NEWS);
//        mDatas = mServerData.getDataFromServer(ServerDataCompl.BEAN_TYPE_NEWS);
        mDatas = mDBOperation.getDataFromLocalDB();
        Log.d("qqe6", "dbOperation table name" + mDBOperation.getmTableName());
        if (mDatas.size() > 0) {
            //成功从数据库读入
            Log.d("qqe in newsView if ", "get table size:" + mDatas.get(0).getTableName());

            Log.d("in encyclopaedia view", "succeed in reading from local db");
        } else {

            //耗时操作
            mDatas = mServerData.getDataFromServer(ServerDataCompl.BEAN_TYPE_NEWS);
            Log.d("qqe", "initAttr: " + (mDatas == null) + " " + mDatas.size());
//            Log.d("qqe in newsView ","get table size:"+ mDatas.get(0).getTableName());
        }
        mNewsAdapter = new NewsAdapter(this, mDatas);

    }

    private void recycleMethod() {
        View footer = View.inflate(this, R.layout.dynamic_botton, null);
        RecyclerViewManager.with(mNewsAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
                .addFooterView(footer)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        Message msg = new Message();
                        msg.what = 17;
                        mHandler.sendMessageDelayed(msg, 2000);
                    }

                    @Override
                    public void onLoadMore() {
                        //上拉加载更多
                        Message msg = new Message();
                        msg.what = 23;
                        mHandler.sendMessageDelayed(msg, 2000);
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(CustomApplication.getContext(), "position:" + position, Toast.LENGTH_SHORT)
                        .show();
            }
        }).into(mRecyclerView, this);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 17:
                    //下拉刷新
//                    for (BmobObject one : mServerData.
//                            getDataFromServer(ServerDataCompl.BEAN_TYPE_NEWS)) {
//                        mDatas.add(one);
//                    }
//                    break;
                case 23:
                    //上拉加载更多
                    break;
            }
            mRecyclerView.onRefreshCompleted();
//            Log.d("qqe news","table name: " + mDBOperation)
            mDBOperation.doSaveDataIntoDB(mDatas);
//            mDatas.clear();
            mNewsAdapter.notifyDataSetChanged();
        }
    };
}
