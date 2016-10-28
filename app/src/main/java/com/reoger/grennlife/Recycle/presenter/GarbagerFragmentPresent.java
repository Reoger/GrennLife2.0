package com.reoger.grennlife.Recycle.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.reoger.grennlife.Recycle.model.TypeGetData;
import com.reoger.grennlife.Recycle.view.IGarbagerFragmentView;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * Created by 24540 on 2016/10/23.
 */
public class GarbagerFragmentPresent implements IGarbagerFragmentPresent {
    private Context context;
    private LocationManager mLocationManager;
    private String mProvider;
    private IGarbagerFragmentView mGarbager;
    private BmobGeoPoint mBmobGeoPoint;

    public GarbagerFragmentPresent(IGarbagerFragmentView mGarbager,Context context) {
        this.mGarbager = mGarbager;
        this.context = context;
    }

    @Override
    public void doGetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        PermissionGen.needPermission((Activity) context,1002,Manifest.permission.ACCESS_FINE_LOCATION);
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = mLocationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            mProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            mProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.d("TAG", "定位失败");
            return;
        }

       Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        if (location != null) {
        mGarbager.onResultLocation(true,location);
        }else{
            log.d("TAG","操，这里为空了？");
            mGarbager.onResultLocation(false,null);
        }
        mLocationManager.requestLocationUpdates(mProvider, 5000, 1, locationListener);
    }

    @Override
    public void doInvailData(Location location) {
        mBmobGeoPoint = new BmobGeoPoint(location.getLongitude(),location.getLatitude());

        BmobQuery<UserMode> query = new BmobQuery<>();
//        query.addWhereNear("gpsAdd",mBmobGeoPoint);//获取最解禁用户的数据
        query.addWhereEqualTo("State",2);
        query.setLimit(10);
        query.order("-createdAt");
        query.findObjects(new FindListener<UserMode>() {
            @Override
            public void done(List<UserMode> list, BmobException e) {
                if(e==null){
                mGarbager.onGetResultData(true, TypeGetData.INITIALZATION,list);
                }else{
                    mGarbager.onGetResultData(false, TypeGetData.INITIALZATION,list);
                    log.d("TAG","查詢失敗");
                }
            }
        });
    }

    @Override
    public void doLoadMoreDate(UserMode userMode) {
        {
            BmobQuery<UserMode> query = new BmobQuery<UserMode>();
                String start = userMode.getCreatedAt();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(start);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
                BmobQuery<UserMode> query1 = new BmobQuery<>();
                query1.addWhereEqualTo("State",2);
                List<BmobQuery<UserMode>> queries = new ArrayList<>();
                queries.add(query);
                queries.add(query1);

                BmobQuery<UserMode> bmobQuery = new BmobQuery<>();
                bmobQuery.and(queries);

                bmobQuery.setLimit(5);
                bmobQuery.order("-createdAt");
                bmobQuery.findObjects(new FindListener<UserMode>() {
                    @Override
                    public void done(List<UserMode> list, BmobException e) {
                        if(e == null){
                            list.remove(0);
                            if(list.size()>0){
                                mGarbager.onGetResultData(true,TypeGetData.INITIALZATION,list);
                            }else {
                                log.d("TAG","没有数据");
                                mGarbager.onGetResultData(true,TypeGetData.INITIALZATION,null);
                            }

                        }else{
                            log.d("TAG", e.toString() + "错误码");
                            mGarbager.onGetResultData(false,TypeGetData.INITIALZATION,list);
                        }
                    }
                });
            }

    }

    @Override
    public void doInvailData() {
        BmobQuery<UserMode> query = new BmobQuery<>();
        query.addWhereEqualTo("State",2);
        query.setLimit(10);
        query.order("-createdAt");
        query.findObjects(new FindListener<UserMode>() {
            @Override
            public void done(List<UserMode> list, BmobException e) {
                if(e==null){
                    mGarbager.onGetResultData(true, TypeGetData.INITIALZATION,list);
                }else{
                    mGarbager.onGetResultData(false, TypeGetData.INITIALZATION,list);
                    log.d("TAG","查詢失敗");
                }
            }
        });
    }

    @Override
    public void doRefeshData(UserMode userMode) {
        {
            BmobQuery<UserMode> query = new BmobQuery<>();
            String start = userMode.getCreatedAt();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
            BmobQuery<UserMode> query1 = new BmobQuery<>();
            query.addWhereEqualTo("State",2);

            List<BmobQuery<UserMode>> queries = new ArrayList<>();
            queries.add(query);
            queries.add(query1);

            BmobQuery<UserMode> bmobQuery = new BmobQuery<>();
            bmobQuery.and(queries);

            bmobQuery.findObjects(new FindListener<UserMode>() {
                @Override
                public void done(List<UserMode> list, BmobException e) {
                    if(e == null){
                        list.remove(0);
                        if(list.size()>0){
                           mGarbager.onGetResultData(true,TypeGetData.REFRESH,list);
                        }else{
                            log.d("TAG","刷新并沒有獲得數據");
                            mGarbager.onGetResultData(true,TypeGetData.REFRESH,null);
                        }

                    }else{
                        log.d("YYY","ggg"+e.toString());
                        mGarbager.onGetResultData(false,TypeGetData.REFRESH,null);
                    }
                }
            });
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            // showLocaion(location);
            if (location != null) {
                mGarbager.onResultLocation(true,location);
                //这里传出location对象
            }else{
                mGarbager.onResultLocation(false,null);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        }

        @Override
        public void onProviderEnabled(String provider) {
            //   ，比如GPS被打开
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };
}
