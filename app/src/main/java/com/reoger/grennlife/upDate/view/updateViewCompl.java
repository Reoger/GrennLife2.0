package com.reoger.grennlife.upDate.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by Zimmerman on 2016/10/25.
 */
public class updateViewCompl extends AppCompatActivity implements IUpdateView  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobUpdateAgent.initAppVersion();
    }
}
