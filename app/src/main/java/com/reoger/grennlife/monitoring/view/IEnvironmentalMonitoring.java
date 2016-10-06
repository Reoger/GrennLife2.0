package com.reoger.grennlife.monitoring.view;

/**
 * Created by 24540 on 2016/9/12.
 */
public interface IEnvironmentalMonitoring {
    void onClear();
    void onGetCurrentLocation(String location);
    //信息上传的结果
    void onLoadResult(int Code,String stats);

}
