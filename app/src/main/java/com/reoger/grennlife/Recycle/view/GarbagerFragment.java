package com.reoger.grennlife.Recycle.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.adapter.GarbagerAdapter;
import com.reoger.grennlife.Recycle.model.TypeGetData;
import com.reoger.grennlife.Recycle.presenter.GarbagerFragmentPresent;
import com.reoger.grennlife.Recycle.presenter.IGarbagerFragmentPresent;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.CustomApplication;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;
import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/26.
 */
public class GarbagerFragment extends Fragment implements IGarbagerFragmentView {

    private ArrayList<UserMode> mDatas = new ArrayList<UserMode>();
    private RefreshRecyclerView recyclerView;
    private GarbagerAdapter mAdapter;
    private View rootView;
    private IGarbagerFragmentPresent mIGarbager;
    private ProgressBar mBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.layout_base_main_recycle, container, false);
        rootView = messageLayout;
        initView();
        return messageLayout;
    }


    private void initView() {
        Context context = getContext();

        mIGarbager = new GarbagerFragmentPresent(this, context);
        PermissionGen.needPermission(this,1002,Manifest.permission.ACCESS_FINE_LOCATION);
        mIGarbager.doGetCurrentLocation();//获取当前位置信息+

        recyclerView = (RefreshRecyclerView) rootView.findViewById(R.id.dynamic_recyclerView);
        mBar = (ProgressBar) rootView.findViewById(R.id.garbager_show_progressbar);

        mAdapter = new GarbagerAdapter(context, mDatas);
        RecyclerViewManager.with(mAdapter, new LinearLayoutManager(context))
                .setMode(RecyclerMode.BOTH)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        if (mDatas.size() > 0) {
                            UserMode userMode = mDatas.get(0);
                            mIGarbager.doRefeshData(userMode);
                        } else {
                            new toast(getActivity(),"刷新完成");
                        }
                    }

                    @Override
                    public void onLoadMore() {
                        int position = mDatas.size() - 1;
                        if (position <= 0) {
                            new toast(getContext(), "加载不可用");
                        } else {
                            UserMode userMode = mDatas.get(position);
                            mIGarbager.doLoadMoreDate(userMode);
                        }
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                showDialog(position);
            }
        }).into(recyclerView, context);
    }

    @Override
    public void onResultLocation(boolean flag, Location location) {

        if (flag) {
            log.d("TAG", "当前的位置信息" + location.getAltitude() + "***" + location.getLongitude());
            CustomApplication customApplication = CustomApplication.getCustomApplication();
            customApplication.setmUserLocation(location);//设置用户信息位全局公用
            mIGarbager.doInvailData(location);
        } else {
            new toast(getActivity(),"定位失败，请检查你的网络连接和定位权限");
        }
    }

    @Override
    public void onGetResultData(boolean flag, TypeGetData type, List<UserMode> lists) {
        mBar.setVisibility(View.GONE);
        if (flag) {
            switch (type) {
                case INITIALZATION:
                    mDatas.clear();
                    mDatas.addAll(lists);
                    break;
                case REFRESH:
                    new toast(getContext(), "刷新成功");
                    break;
                case LOAD_MORE:
                    new toast(getContext(), "加载成功");
                    break;
            }
            recyclerView.onRefreshCompleted();
            mAdapter.notifyDataSetChanged();
        } else {
            new toast(getContext(), "失败了");
        }
    }

    private void showDialog(final int postion) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
        PermissionGen.needPermission(getActivity(),1001, Manifest.permission.CALL_PHONE);
        builder.setMessage("要联系他嘛?");
        builder.setTitle("提示");
        builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String mobile = mDatas.get(postion).getMobilePhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+mobile));
                startActivity(intent);
            }
        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

}