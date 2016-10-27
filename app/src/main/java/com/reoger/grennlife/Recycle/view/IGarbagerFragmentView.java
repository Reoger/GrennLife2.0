package com.reoger.grennlife.Recycle.view;

import android.location.Location;

import com.reoger.grennlife.Recycle.model.TypeGetData;
import com.reoger.grennlife.loginMVP.model.UserMode;

import java.util.List;

/**
 * Created by 24540 on 2016/10/23.
 */
public interface IGarbagerFragmentView {
    //返回获取到的地理信息
    void onResultLocation(boolean flag, Location location);
    void onGetResultData(boolean flag, TypeGetData type, List<UserMode> lists);
}
