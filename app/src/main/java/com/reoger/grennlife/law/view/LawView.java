package com.reoger.grennlife.law.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.law.adapter.LawsViewAdapter;
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
 * Created by Zimmerman on 2016/9/27.
 */
public class LawView extends AppCompatActivity implements ILawView {
    private ArrayList<BmobObject> mDatas;
    private RefreshRecyclerView mLawRecyclerView;
    private LawsViewAdapter mAdapter;

    private IServerData mServerDataCompl;
    //数据库操作
    private IDBOperation mDBOperationComl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_laws_main);
        initAttr();
        initView();
        recycleViewMethod();
    }

    private void initView() {
        mLawRecyclerView = (RefreshRecyclerView) findViewById(R.id.dynamic_recyclerView);
    }

    private void initAttr() {
        Log.d("qqe6",""+ (mDBOperationComl==null) );
        mServerDataCompl = new ServerDataCompl();
        mDBOperationComl = DBOperationCompl.getInstance(this,ServerDataCompl.BEAN_TYPE_LAWS);
        Log.d("qqe7",""+ (mDBOperationComl==null)+mDBOperationComl.getmTableName());

        mDatas = mDBOperationComl.getDataFromLocalDB();
        Log.d("qqw", "finish read data :" + mDatas.size());
        //判定是否需要从网络后台读取数据
        if (mDatas.size() > 0) {
            //成功从数据库读入
            Log.d("in encyclopaedia view", "succeed in reading from local db");
        } else {
            //耗时操作
            mDatas = mServerDataCompl.getDataFromServer(ServerDataCompl.BEAN_TYPE_LAWS);
            Log.d("qqe", "initAttr: " + (mDatas == null)+ " "+mDatas.size());
//            //存入数据库
//            mDBOperationComl.doSaveDataIntoDB(mData);
//            Log.d("qqe","成功存入数据库哦"+mData.size());
        }
        mAdapter = new LawsViewAdapter(this,mDatas);
    }


    private void recycleViewMethod() {
   //     View footer = View.inflate(this, R.layout.dynamic_botton, null);
        RecyclerViewManager.with(mAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
//                .addHeaderView(header)
          //      .addFooterView(footer)
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
                        msg.arg1 = mAdapter.getItemCount();
                        Log.d("qqw", "before handler :" + mDatas.size());
                        mHandler.sendMessageDelayed(msg, 2000);
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(CustomApplication.getContext(), "position:" + position, Toast.LENGTH_SHORT)
                        .show();
            }
        }).into(mLawRecyclerView, this);
    }

    private Handler mHandler = new Handler() {
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
            mLawRecyclerView.onRefreshCompleted();
            Log.d("qqw", "before notify size :" + mDatas.size() + "db table:"+mDBOperationComl.getmTableName());
            //存入数据库

            mDBOperationComl.doSaveDataIntoDB(mDatas);
//            mData.clear();
            mAdapter.notifyDataSetChanged();
        }
    };

}
