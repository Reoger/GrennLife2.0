package com.reoger.grennlife.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.push.PushConstants;

/**
 * Created by 24540 on 2016/10/22.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            android.widget.Toast.makeText(context,"收到推送"+intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
            Tools tools = new Tools();
            tools.showNotification(context,intent.getStringExtra("msg").toString(),"context");
        }
    }

}
