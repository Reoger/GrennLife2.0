package com.reoger.grennlife.monitoring.presenter;

import android.content.Context;

/**
 * Created by 24540 on 2016/9/12.
 */
public interface IMonitoringPresenter {
    /** 获取当前位置信息*/
    void doGetCurrentLocation(Context context);
    /**拍摄现场照片*/
     String doTakePhone(Context context);
    /**图片拍摄完成，剪裁图片*/
    void doCutPircture(Context context);
    /**从相册中选择图片资源**/
    void doGetPhoto(Context context,int requestCode);
    /**取得剪切后图片的资源*/
    void doGetBitmapPicture(Context context);
    //删除没有的图片
    void doDeletePhoto();
    /**上传举报信息*/
    void doUploadMonitoringInfo(String title, String content, String num, String location, String imageUrl);
    /**清除界面填写的元素*/
    void doClear();
}
