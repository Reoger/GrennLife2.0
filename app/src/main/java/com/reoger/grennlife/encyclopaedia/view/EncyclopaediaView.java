package com.reoger.grennlife.encyclopaedia.view;

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
import com.reoger.grennlife.encyclopaedia.adapter.EncyclopaediaAdapter;
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
 * Created by admin on 2016/9/18.
 */
public class EncyclopaediaView extends AppCompatActivity implements IEncyclopaediaView {
    private ArrayList<BmobObject> mData;
    private EncyclopaediaAdapter mEncyclopaediaAdapter;
    private RefreshRecyclerView mRecyclerView;

    private IServerData mServerDataCompl;
    //数据库操作
    private IDBOperation mDBOperationComl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_encyclopaedia_main);
        initAttr();
        initView();
        recycleViewMethod();
        //更新下新闻

    }

    private void initView() {
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.dynamic_recyclerView);

    }


    private void initAttr() {
        mServerDataCompl = new ServerDataCompl();
        mDBOperationComl = DBOperationCompl.getInstance(this, ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA);
//        mData = new ArrayList<>();
        mData = mDBOperationComl.getDataFromLocalDB();
        Log.d("qqw", "finish read data :" + mData.size());
        //判定是否需要从网络后台读取数据
        if (mData.size() > 0) {
            //成功从数据库读入
            mEncyclopaediaAdapter = new EncyclopaediaAdapter(this, mData);
            mEncyclopaediaAdapter.notifyDataSetChanged();
            Log.d("in encyclopaedia view", "succeed in reading from local db");
        } else {
            //耗时操作
            mData = mServerDataCompl.getDataFromServer(ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA,this);
            mEncyclopaediaAdapter = new EncyclopaediaAdapter(this, mData);
        }

    }


    private void recycleViewMethod() {
      //  View footer = View.inflate(this, R.layout.dynamic_botton, null);
        RecyclerViewManager.with(mEncyclopaediaAdapter, new LinearLayoutManager(this))
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
                        msg.arg1 = mEncyclopaediaAdapter.getItemCount();
                        Log.d("qqw", "before handler :" + mData.size());
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
            mEncyclopaediaAdapter.notifyDataSetChanged();
        }
    };
}
