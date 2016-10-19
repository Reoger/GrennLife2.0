package com.reoger.grennlife.Recycle.presenter;

import com.reoger.grennlife.Recycle.model.OldThing;

/**
 * Created by 24540 on 2016/10/16.
 */
public interface IOldThingPresent {
    //初始化数据
    void doInvailData();
    //加载更多数据
    void doLoadMoreDate(OldThing oldThing);
    //更新数据
    void doRefeshData(OldThing oldThing);
}
