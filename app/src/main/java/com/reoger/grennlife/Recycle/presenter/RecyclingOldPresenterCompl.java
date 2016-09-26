package com.reoger.grennlife.Recycle.presenter;

import android.content.Context;
import android.content.Intent;

import com.reoger.grennlife.Recycle.view.GarbagerListView;
import com.reoger.grennlife.Recycle.view.PublishingResourcesView;

/**
 * Created by 24540 on 2016/9/18.
 */

public class RecyclingOldPresenterCompl implements IRecyclingOldPresenter{

    private Context mContext;

    public RecyclingOldPresenterCompl(Context context) {
        this.mContext = context;
    }

    //进入发布资源的界面
    @Override
    public void doComeActivity(int code) {
        Intent intent= null;
        switch (code){
            case 1:
                 intent = new Intent(mContext, PublishingResourcesView.class);
                break;
            case 2:
                intent = new Intent(mContext, GarbagerListView.class);
                break;
        }
        mContext.startActivity(intent);
    }

}
