package com.reoger.grennlife.utils.ServerDataOperation;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zimmerman on 2016/9/24.
 */
public interface IServerData {
    ArrayList<BmobObject> getDataFromServer(int beanType);
    ArrayList<BmobObject> getDataFromServer(int beanType,Activity instanceView);
}
