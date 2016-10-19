package com.reoger.grennlife.user.setting.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reoger.grennlife.loginMVP.view.LoginView;
import com.reoger.grennlife.utils.toast;

import cn.bmob.v3.BmobUser;

/**
 * Created by Zimmerman on 2016/10/19.
 */
public class SettingCompl implements ISettingView {
    private Context mContext;

    public SettingCompl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void logOut() {
        //清除缓存用户对象
        if (BmobUser.getCurrentUser() != null) {
            BmobUser.logOut();
            new toast(mContext, "退出登录！");
            Intent loginIntent = new Intent(mContext,LoginView.class);
            mContext.startActivity(loginIntent);
            ((Activity)mContext).finish();

        } else {
            new toast(mContext, "未登录！");
        }
    }
}
