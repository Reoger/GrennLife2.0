package com.reoger.grennlife.technology.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.technology.adapter.TechnologyAdapter;
import com.reoger.grennlife.technology.model.TechnologyBean;
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
 * Created by admin on 2016/9/28.
 */
public class TechnologyView extends AppCompatActivity {
    private TechnologyAdapter mTechnologyAdapter;
    private RefreshRecyclerView mRecyclerView;
    private ArrayList<BmobObject> mData;

    private IServerData mServerDataCompl;
    private IDBOperation mDBOperationComl;
    private ProgressBar mBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_technology_main);
        initView();
        initAttr();
        recycleViewMethod();
    }

    private void initView() {
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.dynamic_recyclerView);
        mBar = (ProgressBar) findViewById(R.id.technology_content_bar);
    }

    private void initAttr() {
        mServerDataCompl = new ServerDataCompl();
        mDBOperationComl = DBOperationCompl.getInstance(this, ServerDataCompl.BEAN_TYPE_TECHNOLOGY);
//        mData = new ArrayList<>();
        mData = mDBOperationComl.getDataFromLocalDB();
        Log.d("qqw", "finish read data :" + mData.size());
        //判定是否需要从网络后台读取数据
        if (mData.size() > 0) {
            //成功从数据库读入
            mTechnologyAdapter = new TechnologyAdapter(this, mData);
            mTechnologyAdapter.notifyDataSetChanged();
            mBar.setVisibility(View.GONE);
        } else {
            //耗时操作
            mData = mServerDataCompl.getDataFromServer(ServerDataCompl.BEAN_TYPE_TECHNOLOGY,this);
            mTechnologyAdapter = new TechnologyAdapter(this, mData);
            mBar.setVisibility(View.GONE);
        }

    }

    private void recycleViewMethod() {
      //  View footer = View.inflate(this, R.layout.dynamic_botton, null);
        RecyclerViewManager.with(mTechnologyAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
//                .addHeaderView(header)
             //   .addFooterView(footer)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        Message msg = new Message();
                        msg.what = 10;
                        mHandler.sendMessageDelayed(msg, 2000);
                    }

                    //上拉刷新
                    @Override
                    public void onLoadMore() {

                        //存在数据库的时候编下号，每次加载五个
                        Message msg = new Message();
                        msg.what = 23;
                        //当前总的词条数目
                        msg.arg1 = mTechnologyAdapter.getItemCount();
                        Log.d("qqw", "before handler :" + mData.size());
                        mHandler.sendMessageDelayed(msg, 2000);
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                TechnologyBean one = (TechnologyBean) mData.get(position)
                        ;
                Intent technologyIntent = new Intent(getApplicationContext(),TechnologyDetailView.class);
                technologyIntent.putExtra(TechnologyDetailView.ARG_TECHNOLOGY_TITLE,one.getTitle());
                technologyIntent.putExtra(TechnologyDetailView.ARG_TECHNOLOGY_CONTENT,one.getContent());
                startActivity(technologyIntent);

                Toast.makeText(CustomApplication.getContext(), "position:" + position, Toast.LENGTH_SHORT)
                        .show();

            }
        }).into(mRecyclerView, this);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    //下拉刷新要从服务器加载更多数据
//                    for (BmobObject one : mServerDataCompl.getDataFromServer(
//                            ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA
//                    )) {
//                        mData.add(one);
//                    }
                    break;
                case 23:
                    break;
            }
            mRecyclerView.onRefreshCompleted();
            Log.d("qqw", "before notify size :" + mData.size());
            //存入数据库
            mDBOperationComl.doSaveDataIntoDB(mData);
//            mData.clear();
            mTechnologyAdapter.notifyDataSetChanged();
        }
    };
}
