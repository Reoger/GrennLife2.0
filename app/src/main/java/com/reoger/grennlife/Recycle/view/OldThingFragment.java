package com.reoger.grennlife.Recycle.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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
public class OldThingFragment extends Fragment implements IOldthing {

    private List<OldThing> mData = new ArrayList<OldThing>();
    private RefreshRecyclerView recyclerView;
    private OldThingsAdapter mOldthingAdapter;
    private View rootView;
    private IOldThingPresent mOllThingPresent;

    private ImageButton mPublish;
    private ProgressBar mBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.layout_base_main_recycle2, container, false);
        rootView = messageLayout;
        initView();
        return messageLayout;

    }

    private void initView() {
        final Context context = getContext();
        mPublish = (ImageButton) rootView.findViewById(R.id.recycle_add);
        mOllThingPresent = new OldThingPresent(this, context);
        recyclerView = (RefreshRecyclerView) rootView.findViewById(R.id.dynamic_recyclerView2);
        mBar = (ProgressBar) rootView.findViewById(R.id.oldthing_show_progressbar);
        //需要先初始化数据
        mOllThingPresent.doGetData(TypeGetData.INITIALZATION);
        mOldthingAdapter = new OldThingsAdapter(context, mData);
        RecyclerViewManager.with(mOldthingAdapter, new LinearLayoutManager(context))
                .setMode(RecyclerMode.BOTH)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        mOllThingPresent.doGetData(TypeGetData.REFRESH);
                    }

                    @Override
                    public void onLoadMore() {
                        mOllThingPresent.doGetData(TypeGetData.LOAD_MORE);
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                android.widget.Toast.makeText(getContext(), "item" + position, android.widget.Toast.LENGTH_SHORT).show();
            }
        }).into(recyclerView, context);
        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,PublishingResourcesView.class));
            }
        });
    }

    //这里回传获取到的数据
    @Override
    public void onGetResultData(boolean flag, TypeGetData type, List<OldThing> lists) {
        mBar.setVisibility(View.GONE);
        if (flag) {
            switch (type) {
                case INITIALZATION:
                    mData.clear();
                    mData.addAll(lists);
                    break;
                case LOAD_MORE:
                    if(mData.size()==lists.size()){
                        new toast(getActivity(),"暂时没有更多的数据可以加载");
                    }else{
                        mData.clear();
                        mData.addAll(lists);
                    }
                    break;
                case REFRESH:
                    mData.clear();
                    mData.addAll(lists);
                    break;
            }
                mOldthingAdapter.notifyDataSetChanged();
        } else {
           new toast(getActivity(),"加载失败");
        }
        recyclerView.onRefreshCompleted();
    }
}
