package com.reoger.grennlife.user.setting.presenter;

import android.content.Context;

/**
 * Created by Zimmerman on 2016/10/19.
 */
public interface ISettingView {
    void logOut();
    void doAppShare(Context mContext);
    void doClearData(Context context);
}
