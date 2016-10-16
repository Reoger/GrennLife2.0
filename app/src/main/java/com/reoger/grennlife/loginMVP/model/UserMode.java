package com.reoger.grennlife.loginMVP.model;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by 24540 on 2016/9/10.
 */
public class UserMode extends BmobUser implements IUserModel {

    private String ReallyName;
    private String Id;
    private String locations;
    private Integer State;
    private BmobGeoPoint gpsAdd;

    public String getReallyName() {
        return ReallyName;
    }

    public void setReallyName(String name) {
        ReallyName = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public BmobGeoPoint getGpsAdd() {
        return gpsAdd;
    }

    public void setGpsAdd(BmobGeoPoint gpsAdd) {
        this.gpsAdd = gpsAdd;
    }

    /**
     * 1.表示审核中
     * 2.审核通过
     * 3.审核不通过
     *
     * @return
     */

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }


}
