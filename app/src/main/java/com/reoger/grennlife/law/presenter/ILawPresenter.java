package com.reoger.grennlife.law.presenter;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zimmerman on 2016/9/27.
 */
public interface ILawPresenter {
    //在ArrayList中取出前n个数据，并返回包含这前n个数据的ArrayList
    public ArrayList<BmobObject> getListsFormer (ArrayList<BmobObject> datas,int n);
}
