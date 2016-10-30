package com.reoger.grennlife.Recycle.presenter;

import android.location.Location;

import com.reoger.grennlife.Recycle.model.TypeGetData;

/**
 * Created by 24540 on 2016/10/23.
 */
public interface IGarbagerFragmentPresent {
     void doGetCurrentLocation();
     void doGetDateByLocation(TypeGetData data, Location location);
}
