package com.reoger.grennlife.utils;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.view.WindowManager;

import com.reoger.grennlife.loginMVP.model.UserMode;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by 24540 on 2016/9/19.
 * 继承Application
 */
public class CustomApplication extends Application{

private static CustomApplication application = null;

    public static CustomApplication getCustomApplication(){
        return application;
    }


    private String userName;
    private UserMode userMode;
    //全局context
    private static Context context;

//        杰哥的
//    private final static String APP_ID = "bd11c4f379b3dce8ec40ed462494ba25";
      public final static String APP_ID = "92df55f29a323c8c205a15c39d0c63bc";
    private Location mUserLocation;

    private int mWidth;
    private int mHeight;

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
    public Location getmUserLocation() {
        return mUserLocation;
    }
    public void setmUserLocation(Location mUserLocation) {
        this.mUserLocation = mUserLocation;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public static CustomApplication getApplication() {
        return application;
    }

    public static void setApplication(CustomApplication application) {
        CustomApplication.application = application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //在这里进行初始化工作
        context = getApplicationContext();
        //bmob初始化
        Bmob.initialize(this, APP_ID);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);
        //SharedSDK初始化(xml方式配置好应用)
        ShareSDK.initSDK(this);
        initData();
        application = this;
    }

    private void initData() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        this.mWidth = wm.getDefaultDisplay().getWidth();
        this.mHeight = wm.getDefaultDisplay().getHeight();//初始化获取屏幕得宽和高
    }


}
