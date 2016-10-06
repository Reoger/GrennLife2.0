package com.reoger.grennlife.monitoring.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.monitoring.Model.ReportInfo;
import com.reoger.grennlife.monitoring.view.IEnvironmentalMonitoring;
import com.reoger.grennlife.utils.GetPhoto;
import com.reoger.grennlife.utils.log;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
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

    private String bomeFileUrl;
    private ReportInfo reportInfo;

    private final static int UPLOAD_SUCESS = 123;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPLOAD_SUCESS:
                    reportInfo.setImageUrl(bomeFileUrl);
                    reportInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                log.d("TAG","保存成功");
                                mIEnvironmentalMonitoring.onLoadResult(1,s);
                            }else{
                                log.d("TAG","保存失败"+"dynamic"+e.getMessage());
                            //    mIDynamicView.onPublishResult(0,s);
                                mIEnvironmentalMonitoring.onLoadResult(2,s);
                            }
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public MonitoringPresenterCompl(IEnvironmentalMonitoring mIEnvironmentalMonitoring,Context context ) {
        this.mIEnvironmentalMonitoring = mIEnvironmentalMonitoring;
        this.context = context;
        mGetPhoto = new GetPhoto(context);
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    @Override
    public void doAddPhoto(int resuletCode) {
        // 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(3)
                .build();
        ImgSelActivity.startActivity((Activity) context, config, resuletCode);
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








    //上传举报信息，
    // 1.首先将图片上传，然后获取图片的uri
    // 2将标题，内容，联系方式，位置，和图片的url上传到服务器.
    @Override
    public void doUploadMonitoringInfo(String title, String content, String num, String location, List<String> imageUrl) {
        log.d("YYY", "tttUPLOAD");




        reportInfo = new ReportInfo();
        reportInfo.setTitle(title);
        reportInfo.setContent(content);
        reportInfo.setNum(num);
        UserMode userMode = BmobUser.getCurrentUser(UserMode.class);
        reportInfo.setUserMode(userMode);
        reportInfo.setStatuts(1);//默认上传为未审核状态

        if(imageUrl == null){
            reportInfo.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e == null){
                        log.d("TAG","保存成功");
                        mIEnvironmentalMonitoring.onLoadResult(1,s);
                    }else{
                        log.d("TAG","保存失败"+"dynamic"+e.getMessage());
                        mIEnvironmentalMonitoring.onLoadResult(2,s);
                    }
                }
            });
        }else{
            String []filesPath = new String[imageUrl.size()];
            int  j=0;
            for (String item :
                    imageUrl) {
                filesPath[j] = item;
                j++;
            }
            doUploadFiles(filesPath);//上传图片到云端
        }

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

    /**
     * 上传多张图片
     * @param filePaths
     */
    synchronized String doUploadFiles(final String[] filePaths){

        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(final List<BmobFile> files,List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                    log.d("TAG",urls.toString());
                    bomeFileUrl = urls.toString();
                    Message message = new Message();
                    message.what = UPLOAD_SUCESS;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                log.d("TAg","错误码"+statuscode +",错误描述："+errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                log.d("TAG","进度 "+curIndex+" :"+curPercent+" :"+total+": "+totalPercent);
                //这里可以用handler将进度信息传出来，反馈给用户
            }
        });

        return bomeFileUrl;
    }

}
