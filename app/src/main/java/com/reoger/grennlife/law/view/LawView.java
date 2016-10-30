package com.reoger.grennlife.law.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.reoger.grennlife.R;
import com.reoger.grennlife.law.adapter.LawsViewAdapter;
import com.reoger.grennlife.law.model.LawsBean;
import com.reoger.grennlife.law.presenter.ILawPresenter;
import com.reoger.grennlife.law.presenter.PresenterComl;
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
    //所有数据
    private LawsViewAdapter mAdapter;
    private ILawPresenter mLawPresenter;

    //初次显示的数据数量
    private int mCurrentAdatperCounts = 5;
    //网络后台操作类
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
        mLawPresenter = new PresenterComl();
        mServerDataCompl = new ServerDataCompl();
        mDBOperationComl = DBOperationCompl.getInstance(this,ServerDataCompl.BEAN_TYPE_LAWS);
        Log.d("qqe7",""+ (mDBOperationComl==null)+mDBOperationComl.getmTableName());

        mDatas = mDBOperationComl.getDataFromLocalDB();
        Log.d("qqw", "finish read data :" + mDatas.size());
        //判定是否需要从网络后台读取数据
        if (mDatas.size() > 0) {
            //成功从数据库读入
            mAdapter = new LawsViewAdapter(this,mDatas);
            mAdapter.notifyDataSetChanged();
            Log.d("in encyclopaedia view", "succeed in reading from local db");
        } else {
            //耗时操作
            mDatas = mServerDataCompl.getDataFromServer(ServerDataCompl.BEAN_TYPE_LAWS,this);
            mAdapter = new LawsViewAdapter(this,mDatas);
        }
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
                        Message msg = new Message();
                        msg.what = 23;
                        Log.d("qqw", "before handler :" + mDatas.size());
                        mHandler.sendMessageDelayed(msg, 2000);
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                Intent mLawIntent = new Intent(getApplicationContext(),LawDetailView.class);
                mLawIntent.putExtra(LawDetailView.ARG_LAWS_TITLE,
                        ((LawsBean)mDatas.get(position)).getTitle()
                );
                mLawIntent.putExtra(LawDetailView.ARG_LAWS_CONTENT,
                        ((LawsBean)mDatas.get(position)).getContent()
                );
                startActivity(mLawIntent);

            }
        }).into(mLawRecyclerView, this);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    //下拉刷新要从服务器加载更多数据
                    Log.d("qqeq","enter");
//                    ArrayList<BmobObject> aDatas;
//                    for(BmobObject one : aDatas = mServerDataCompl.getDataFromServer(
//                            ServerDataCompl.BEAN_TYPE_LAWS
//                    )) {
//                        //如果本地数组不包含新得到的数据，那么就添加进本地数组
//                        Log.d("qqeq",""+ aDatas.size());
//                        if (!mDatas.contains(one) ) {
//                            Log.d("qqeq","no contain");
//                            mDatas.add(0,one);
//                        }
//                    }
                    break;
                case 23:
//                    mAdapter = new LawsViewAdapter(,mLawPresenter.getListsFormer(mDatas,mCurrentAdatperCounts));
                    break;
            }
            mLawRecyclerView.onRefreshCompleted();
            Log.d("qqw", "before notify size :" + mDatas.size() + "db table:"+mDBOperationComl.getmTableName());
            //存入数据库

            mDBOperationComl.doSaveDataIntoDB(mDatas);
            mAdapter.notifyDataSetChanged();
        }
    };

}
