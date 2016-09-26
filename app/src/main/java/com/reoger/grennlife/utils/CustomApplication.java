package com.reoger.grennlife.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by 24540 on 2016/9/19.
 * 继承Application
 */
public class CustomApplication extends Application{
    private String userName;
    private String phoneNum;
    //全局context
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //在这里进行初始化工作
        context = getApplicationContext();
    }

}
