package com.reoger.grennlife.user.infomation.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.user.infomation.View.IInfomationView;
import com.reoger.grennlife.utils.log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by 24540 on 2016/10/12.
 */
public class InfomationPresenterCompl implements InfomationPresenter {
    private LocationManager mLocationManager;
    private String mProvider;
    private IInfomationView mIInfomationView;
    private Context context;
    private Location  location;


    public InfomationPresenterCompl(IInfomationView mIInfomationView, Context context) {
        this.mIInfomationView = mIInfomationView;
        this.context = context;
    }

    @Override
    public void doGetCurrentLocation(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = mLocationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            mProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            mProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.d("TAG", "定位失败");
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
          location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        if (location != null) {
            mIInfomationView.onGetLocation(true,location);
            log.d("Tag","location"+location.getLatitude()+"::location.getLongitude()"+location.getLongitude()+"::"+
                    location.getAltitude());
        }else{
            log.d("TAG","操，这里为空了？");
            mIInfomationView.onGetLocation(false,null);
        }
        mLocationManager.requestLocationUpdates(mProvider, 5000, 1, locationListener);
    }

    @Override
    public void doAuthentication(String name, String Id, String address,String introduction) {
        UserMode userMode = BmobUser.getCurrentUser(UserMode.class);
        UserMode user = new UserMode();
        user.setObjectId(userMode.getObjectId());
        user.setReallyName(name);
        user.setId(Id);
        user.setState(1);
        user.setIntroduction(introduction);
        user.setLocations(address);
        if(location!= null){
            BmobGeoPoint point = new BmobGeoPoint(location.getLongitude(),location.getLatitude());
           //经度-180-180 location.getAltitude()
            //维度 0-90 location.getLongitude()
            user.setGpsAdd(point);
        }

        if(location!= null){
            log.d("TAG","location.getLatitude()"+location.getLatitude()+"::location.getAltitude()"+location.getAltitude()
            +"location.getLongitude()"+location.getLongitude());
        }

        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    mIInfomationView.onGetUpdataUserInfo(true,null);
                    log.d("TAG","成功更新数据");
                }else{
                    log.d("TAg","更新失败"+e.toString());
                    mIInfomationView.onGetUpdataUserInfo(false,e.toString());
                }
            }
        });

    }

    //更新个人信息
    @Override
    public void doUpdataUserInfo() {
        UserMode userMode = BmobUser.getCurrentUser(UserMode.class);
        BmobQuery<UserMode> query = new BmobQuery<>();
        query.addWhereEqualTo("username",userMode.getUsername());
        query.findObjects(new FindListener<UserMode>() {
            @Override
            public void done(List<UserMode> list, BmobException e) {
                if(e == null ){
                    log.d("TTT","数据查询陈宫123456789");
                    if(list.get(0)!=null){
                        mIInfomationView.onGetUserMode(true,list.get(0));
                    }else{
                        mIInfomationView.onGetUserMode(true,null);
                    }
                }else{
                    mIInfomationView.onGetUserMode(false,null);
                    log.d("TTT","数据查询陈宫1234567*-*-*-*89");
                }
            }
        });
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            // showLocaion(location);
            if (location != null) {
                mIInfomationView.onGetLocation(true,location);
                //这里传出location对象
            }else{
                mIInfomationView.onGetLocation(false,null);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        }

        @Override
        public void onProviderEnabled(String provider) {
            //  Provider被enable时触发此函数，比如GPS被打开
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };
}
