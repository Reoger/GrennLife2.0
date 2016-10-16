package com.reoger.grennlife.law.presenter;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zimmerman on 2016/9/27.
 */
public class PresenterComl implements ILawPresenter {

    //上啦刷新思路，可以在加载数据库的时候每次限定，由于数据库的指针式static的所以再去加载的时候位置应该不变
    //直接限定数据库的读取数量即可
    @Override
    public ArrayList<BmobObject> getListsFormer(ArrayList<BmobObject> datas, int n) {
        ArrayList<BmobObject> newDatas = new ArrayList<>();
        if (datas.size() >= n) {
            for (int i=0;i<n;i++) {
                newDatas.add(datas.get(n));
            }
        } else {
            Log.e("presenterComl","the variable 'n' is bigger than primitive datas");
            return null;
        }
        return newDatas;
    }
}
