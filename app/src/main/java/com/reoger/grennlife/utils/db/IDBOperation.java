package com.reoger.grennlife.utils.db;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/9/25.
 */
public interface IDBOperation {
    // 不重复的将一组数据存入数据库
    void doSaveDataIntoDB(ArrayList<BmobObject> datas);
    //从数据库读取信息
    ArrayList<BmobObject> getDataFromLocalDB();
    int getmBeanType();

    String getmTableName();
}
