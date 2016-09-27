package com.reoger.grennlife.utils.ServerDataOperation;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zimmerman on 2016/9/24.
 */
public interface IServerData {
    ArrayList<BmobObject> getDataFromServer(int beanType);
}
