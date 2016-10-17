package com.reoger.grennlife.Recycle.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.adapter.OldThingsAdapter;
import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.Recycle.model.TypeGetData;
import com.reoger.grennlife.Recycle.presenter.IOldThingPresent;
import com.reoger.grennlife.Recycle.presenter.OldThingPresent;
import com.reoger.grennlife.utils.toast;

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
public class OldThingFragment extends Fragment implements IOldthing{

    private List<OldThing> mData = new ArrayList<OldThing>();
    private RefreshRecyclerView recyclerView;
    private OldThingsAdapter mOldthingAdapter;
    private View rootView;
    private IOldThingPresent mOllThingPresent;




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
        mOllThingPresent = new OldThingPresent(this,context);
        View header = View.inflate(context,R.layout.recycle_header2_tete,null);
        recyclerView = (RefreshRecyclerView) rootView.findViewById(R.id.dynamic_recyclerView2);
        //需要先初始化数据
        mOllThingPresent.doInvailData();
        mOldthingAdapter = new OldThingsAdapter(context,mData);
        RecyclerViewManager.with(mOldthingAdapter,new LinearLayoutManager(context))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        if(mData.size()>0){
                            mOllThingPresent.doRefeshData(mData.get(0));
                        }else{
                            new toast(getContext(),"刷新不可用");
                            recyclerView.onRefreshCompleted();
                        }
                    }

                    @Override
                    public void onLoadMore() {
                        //这里添加上拉加载更多的逻辑
//                        int position = mData.size()-1;
//                        if(position < 0 ){
//                            new toast(getContext(),"加载不可用");
//                            recyclerView.onRefreshCompleted();
//                        }else{
//                            mOllThingPresent.doLoadMoreDate(mData.get(position));
//                        }
                            new toast(getContext(),"触发加载更多");
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                android.widget.Toast.makeText(getContext(), "item" + position, android.widget.Toast.LENGTH_SHORT).show();
            }
        }).into(recyclerView,context);
    }

    //这里回传获取到的数据
    @Override
    public void onGetResultData(boolean flag, TypeGetData type, List<OldThing> lists) {
            if(flag){
                switch (type){
                    case  INITIALZATION:
                        new toast(getContext(),"添加数据成功");
                        break;
                    case LOAD_MORE:
                        new toast(getContext(),"加载更多成功");
                        break;
                    case REFRESH:
                        new toast(getContext(),"刷新成功");
                        break;
                }
                if(lists.size()>0){
                    mData.addAll(lists);
                    mOldthingAdapter.notifyDataSetChanged();
                }
            }else{
                switch (type) {
                    case INITIALZATION:
                        new toast(getContext(), "添加数据失败");
                        break;
                    case LOAD_MORE:
                        new toast(getContext(), "加载更多失败");
                        break;
                    case REFRESH:
                        new toast(getContext(), "刷新失败");
                        break;
                }
            }
        recyclerView.onRefreshCompleted();
    }
}
