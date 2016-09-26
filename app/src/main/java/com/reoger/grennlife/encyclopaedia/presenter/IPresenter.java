package com.reoger.grennlife.encyclopaedia.presenter;

import android.content.Context;

import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;

import java.util.ArrayList;

/**
 * Created by Zimmerman on 2016/9/19.
 */
public interface IPresenter {
    //初始化后台网络数据库，直接创建javaBean对应的数据表
    void doInitDBForJavaBean();
    ArrayList<EncyclopaediaBean> getEncyclopaediaData();
    //每次加载五个
    ArrayList<EncyclopaediaBean> getDataFromDB(Context context);

    /**
     * 如果本地数据库为空，那么在加载完网上数据库后就要将数据存入本地数据库了
     */
    void doSaveDataIntoDB(ArrayList<EncyclopaediaBean> datas,Context context);
    void doReadMoreDataFromNetServe(Context context);
}
