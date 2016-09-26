package com.reoger.grennlife.monitoring.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.reoger.grennlife.monitoring.Model.ReportInfo;
import com.reoger.grennlife.monitoring.view.IEnvironmentalMonitoring;
import com.reoger.grennlife.utils.GetPhoto;
import com.reoger.grennlife.utils.Tools;
import com.reoger.grennlife.utils.log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 24540 on 2016/9/12.
 */
public class MonitoringPresenterCompl implements IMonitoringPresenter {
    private IEnvironmentalMonitoring mIEnvironmentalMonitoring;

    private LocationManager mLocationManager;
    private String mProvider;

    private GetPhoto mGetPhoto;
    private Context context;

    public MonitoringPresenterCompl(IEnvironmentalMonitoring mIEnvironmentalMonitoring,Context context ) {
        this.mIEnvironmentalMonitoring = mIEnvironmentalMonitoring;
        this.context = context;
        mGetPhoto = new GetPhoto(context);
    }

    /**
     * 获取当前位置信息
     */
    @Override
    public void doGetCurrentLocation(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = mLocationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            mProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            mProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            //Toast.makeText(this, "定位失败~", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "定位失败");
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Location location = mLocationManager.getLastKnownLocation(mProvider);
        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {

            showLocation(location);

        }
        mLocationManager.requestLocationUpdates(mProvider, 5000, 1, locationListener);
    }

    /**
     * 拍照
     */
    @Override
    public String doTakePhone(Context context) {
        mGetPhoto.takePhotoByCamera();
        return "))";
    }

    /**
     * 剪裁图片
     */
    @Override
    public void doCutPircture(Context context) {
        mGetPhoto.cutImageByCamera();
    }
    /**
     * 从相册中选择
     * @param context
     */
    @Override
    public void doGetPhoto(Context context, int requestCode) {
        mGetPhoto.takePhotobyAlbum();
    }

    /**
     * 获取图片的信息
     * @param context
     */
    @Override
    public void doGetBitmapPicture(Context context ) {
            Bitmap bitmap =mGetPhoto.decodeBitmap();
            mIEnvironmentalMonitoring.onShowPicture(bitmap);
    }

    @Override
    public void doDeletePhoto() {
        // 因为在无任何操作返回时，系统依然会创建一个文件，这里就是删除那个产生的文件
        if(mGetPhoto.picFile != null){
            mGetPhoto.picFile.delete();
        }
    }

    //上传举报信息，
    // 1.首先将图片上传，然后获取图片的uri
    // 2将标题，内容，联系方式，位置，和图片的url上传到服务器.
    @Override
    public void doUploadMonitoringInfo(String title, String content, String num, String location, String imageUrl) {
        log.d("YYY", "tttUPLOAD");

        //log.d("TAG","mGetPhoto.photoUri.toString();"+mGetPhoto.photoUri.toString());
        final  String urlImage="sdcard/greenLife/1744.png";
        Tools tools = new Tools();
        tools.upLoadFile(urlImage);


        ReportInfo info = new ReportInfo();
        info.setTitle(title);
        info.setContent(content);
        info.setNum(num);
        log.d("YYY", "tttSAVE");
        info.save(new SaveListener<String>() {
            public static final String TAG = "ReportInfo";

            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    log.i(TAG, "保存成功");
                } else {
                    log.i(TAG, "保存失败" + s);
                }
            }
        });
    }

    @Override
    public void doClear() {
        mIEnvironmentalMonitoring.onClear();
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            // showLocaion(location);
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

                    private void showLocation(final Location location) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder url = new StringBuilder();
                                url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
                                url.append(location.getLatitude()).append(",");
                                url.append(location.getLongitude());
                                url.append("&sensor=false");
                                final String s = new String(url);
                                Log.d("TAG", s + "  地址");
                                //创建一个okHttpClient对象
                                OkHttpClient okHttpClient = new OkHttpClient();
                                //创建一个Request
                                final Request request = new Request.Builder().url(s)
                                        .addHeader("Accept-Language", "zh-CN").build();
                                //new Call
                                Call call = okHttpClient.newCall(request);
                                //请求加入调度
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.d("TAG", e + "失败");
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String code = response.body().string();
                                        Log.d("TAG", code);
                                        try {
                                            JSONObject jsonObject = new JSONObject(code);
                                            JSONArray resultArray = jsonObject.getJSONArray("results");
                                            if (resultArray.length() > 0) {
                                                JSONObject subObject = resultArray.getJSONObject(0);
                                                //取出格式化后的位置信息
                                                String address = subObject.getString("formatted_address");
                                                log.d("TAG", address);
                                //在这里将address传递出去
                                mIEnvironmentalMonitoring.onGetCurrentLocation(address);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }


}
