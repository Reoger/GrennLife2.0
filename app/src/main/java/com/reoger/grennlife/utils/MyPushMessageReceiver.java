package com.reoger.grennlife.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.bean.jsonBean;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.BmobUser;

/**
 * Created by 24540 on 2016/10/22.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){

            jsonBean gson = new Gson().fromJson(intent.getStringExtra("msg"),jsonBean.class);//解析gson对象
            UserMode userMode = BmobUser.getCurrentUser(UserMode.class);
            log.d("TAG","输出的是objectid"+gson.getObjectId());
            if(userMode.getObjectId().equals(gson.getObjectId())){
                Tools tools = new Tools();
                tools.showNotification(context,gson.getTitle(),gson.getContext(),gson.getUrl());
            }
        }
    }


}
