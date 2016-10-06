package com.reoger.grennlife.monitoring.presenter;

import android.content.Context;

import java.util.List;

/**
 * Created by 24540 on 2016/9/12.
 */
public interface IMonitoringPresenter {
    //添加图片
    void doAddPhoto(int resuletCode);
    /** 获取当前位置信息*/
    void doGetCurrentLocation(Context context);
    /**上传举报信息*/
    void doUploadMonitoringInfo(String title, String content, String num, String location, List<String> imageUrl);
    /**清除界面填写的元素*/
    void doClear();
}
