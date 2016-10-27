package com.reoger.grennlife.Recycle.adapter;

import com.reoger.grennlife.Recycle.model.Garbager;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 24540 on 2016/9/20.
 */
public class Datas {

    UserMode user;
    List<Garbager> garbagers = new ArrayList<>();

    /**
     * 获取垃圾回收的数据
     * 1.首先从本地获取
     * 2.后台联网更新
     */
    public  List<Garbager> getData(){
        BmobQuery<Garbager> query = new BmobQuery<Garbager>();
        query.addWhereEndsWith("title","title");
        query.setLimit(10);
        query.findObjects(new FindListener<Garbager>() {
            @Override
            public void done(List<Garbager> list, BmobException e) {
                if(e==null){
                    log.d("bmob","查询成功，共查询到"+list.size()+"条数据");
                    for (Garbager item :
                            list) {
                        log.d("bmob","数据"+item.getContent());
                    }
                    garbagers = list;
                }else{
                    log.d("bmob","数据查询失败");
                }
            }
        });
        return garbagers;
    }

    /**
     * google maps的脚本里代码
     */
    private static double EARTH_RADIUS = 6378.137;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }
    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     * （a经度，a纬度 b经度，b纬度）
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

}
