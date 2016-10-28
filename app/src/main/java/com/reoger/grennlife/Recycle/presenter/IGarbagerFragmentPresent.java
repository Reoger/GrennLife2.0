package com.reoger.grennlife.Recycle.presenter;

import android.location.Location;

import com.reoger.grennlife.loginMVP.model.UserMode;

/**
 * Created by 24540 on 2016/10/23.
 */
public interface IGarbagerFragmentPresent {
     void doGetCurrentLocation();
     //初始化数据
     void doInvailData(Location location);
     //加载更多数据
     void doLoadMoreDate(UserMode userMode);
     void doInvailData();
     //更新数据
     void doRefeshData(UserMode userMode);
}
