package com.reoger.grennlife.user.infomation.View;

import android.location.Location;

import com.reoger.grennlife.loginMVP.model.UserMode;

/**
 * Created by 24540 on 2016/10/12.
 */
public interface IInfomationView {
    void onGetLocation(boolean flay,Location location);
    void onGetUpdataUserInfo(boolean flag,String Code);
    void onGetUserMode(boolean flag, UserMode usermode);
}
