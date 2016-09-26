package com.reoger.grennlife.encyclopaedia.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.encyclopaedia.adapter.EncyclopaediaAdapter;
import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;
import com.reoger.grennlife.encyclopaedia.presenter.IPresenter;
import com.reoger.grennlife.encyclopaedia.presenter.PresenterCompl;
import com.reoger.grennlife.utils.CustomApplication;

import java.util.ArrayList;

import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by admin on 2016/9/18.
 */
public class EncyclopaediaView extends AppCompatActivity implements IEncyclopaediaView {
    private ArrayList<EncyclopaediaBean> mData;
    private EncyclopaediaAdapter mEncyclopaediaAdapter;
    private RefreshRecyclerView mRecyclerView;

    private IPresenter mPresenter;

    private android.app.ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_encyclopaedia_main);
        initAttr();
        initView();

        initData();

//        mPresenter.doInitDBForJavaBean();
        recycleViewMethod();
    }

    private void initView() {
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.dynamic_recyclerView);

    }

    @Override
    public void initData() {
        mData = mPresenter.getDataFromDB(this);
        if ( mData.size() >0 ) {
            //成功从数据库读入
            Log.d("in encyclopaedia view","succeed in reading from local db");
        } else {
            //耗时操作
            mData = mPresenter.getEncyclopaediaData();
            mPresenter.doSaveDataIntoDB(mData,this);
        }
    }

    private void initAttr() {
        mPresenter = new PresenterCompl();
//        mData = mPresenter.getEncyclopaediaData();
    }


    private void recycleViewMethod() {

//        View header = View.inflate(this,R.layout.dynamic_header,null);
        View footer = View.inflate(this, R.layout.dynamic_botton, null);
        mEncyclopaediaAdapter = new EncyclopaediaAdapter(this, mData);
        RecyclerViewManager.with(mEncyclopaediaAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
//                .addHeaderView(header)
                .addFooterView(footer)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        Message msg = new Message();
                        msg.what = 10;
                        mHandler.sendMessageDelayed(msg, 2000);
                    }

                    //下拉刷新
                    @Override
                    public void onLoadMore() {

                        //存在数据库的时候编下号，每次加载五个
                        Message msg = new Message();
                        msg.what = 23;
                        //当前总的词条数目
                        msg.arg1 = mEncyclopaediaAdapter.getItemCount();
                        mHandler.sendMessageDelayed(msg, 2000);
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
//                android.widget.Toast.makeText(MainActivity.this, "item" + position, android.widget.Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomApplication.getContext(), "position:" + position, Toast.LENGTH_SHORT)
                        .show();
            }
        }).into(mRecyclerView, this);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what){
                    case 10:
                        break;
                    case 23:
                        for (EncyclopaediaBean one : mPresenter.getDataFromDB(CustomApplication.getContext())){
                            mData.add(one);
                        }
                        break;
                }
            mRecyclerView.onRefreshCompleted();
            mEncyclopaediaAdapter.notifyDataSetChanged();
        }
    };
}
