package com.reoger.grennlife.monitoring.view;

import android.graphics.Bitmap;

/**
 * Created by 24540 on 2016/9/12.
 */
public interface IEnvironmentalMonitoring {
    void onClear();
    void onGetCurrentLocation(String location);
    void onShowPicture(Bitmap bitmap);

}
