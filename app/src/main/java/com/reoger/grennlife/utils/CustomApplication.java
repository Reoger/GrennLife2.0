package com.reoger.grennlife.utils;

import android.app.Application;
import android.content.Context;

import com.reoger.grennlife.loginMVP.model.UserMode;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by 24540 on 2016/9/19.
 * 继承Application
 */
public class CustomApplication extends Application{


    private String userName;
    private UserMode userMode;
    //全局context
    private static Context context;

    //    杰哥的
    private final static String APP_ID = "bd11c4f379b3dce8ec40ed462494ba25";
//      public final static String APP_ID = "92df55f29a323c8c205a15c39d0c63bc";
    //W850565405@163.COM  这个账号的 短信资源不多
//    public final static String APP_ID = "9d714aa8912264ebcaae79ad4db067ab";

    public static Context getContext() {
        return context;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public UserMode getUserMode() {
        return userMode;
    }
    public void setUserMode(UserMode userMode) {
        this.userMode = userMode;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        //在这里进行初始化工作
        //bmob初始化
        Bmob.initialize(this, APP_ID);
        //SharedSDK初始化(xml方式配置好应用)
        ShareSDK.initSDK(this);
        context = getApplicationContext();
    }


}
