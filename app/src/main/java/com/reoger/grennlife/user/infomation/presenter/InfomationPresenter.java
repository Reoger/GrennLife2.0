package com.reoger.grennlife.user.infomation.presenter;

import android.content.Context;

/**
 * Created by 24540 on 2016/10/12.
 */
public interface InfomationPresenter {
    /** 获取当前位置信息*/
    void doGetCurrentLocation(Context context);
    void doAuthentication(String name,String Id,String address);
}
