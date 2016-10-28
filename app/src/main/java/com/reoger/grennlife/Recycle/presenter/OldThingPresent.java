package com.reoger.grennlife.Recycle.presenter;

import android.content.Context;

import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.Recycle.model.TypeGetData;
import com.reoger.grennlife.Recycle.view.IOldthing;
import com.reoger.grennlife.utils.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 24540 on 2016/10/16.
 */
public class OldThingPresent implements IOldThingPresent{

    private IOldthing mIOldthing;
    private Context mContext;

    public OldThingPresent(IOldthing mIOldthing, Context mContext) {
        this.mIOldthing = mIOldthing;
        this.mContext = mContext;
    }

    //在这里获取相关数据
    @Override
    public void doInvailData() {
        BmobQuery<OldThing> query = new BmobQuery<>();
        query.addWhereNotEqualTo("title","null");
        query.order("-createdAt");
        query.include("author");
        query.setLimit(10);
        query.findObjects(new FindListener<OldThing>() {
            @Override
            public void done(List<OldThing> list, BmobException e) {
                if(e == null){
                    log.d("TAG","查询旧物数据陈宫");
                    mIOldthing.onGetResultData(true,TypeGetData.INITIALZATION,list);
                }else{
                    mIOldthing.onGetResultData(false,TypeGetData.INITIALZATION,null);
                }
            }
        });
    }

    //加载更多
    @Override
    public void doLoadMoreDate(OldThing oldThing) {
        BmobQuery<OldThing> query = new BmobQuery<>();
        String start = oldThing.getCreatedAt();
        query.include("author");
       query.order("-createdAt");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.d("TAG","时间是"+date);
        query.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date));
        query.setLimit(5);
        query.findObjects(new FindListener<OldThing>() {
            @Override
            public void done(List<OldThing> list, BmobException e) {
                if(e == null){
                    if(list.size()>1){
                        list.remove(0);
                        mIOldthing.onGetResultData(true,TypeGetData.LOAD_MORE,list);
                    }else{
                        log.d("TAG","数据查询成功，但是没有数据可以添加a");
                        mIOldthing.onGetResultData(false,TypeGetData.LOAD_MORE,null);
                    }
                }else{
                    mIOldthing.onGetResultData(false,TypeGetData.LOAD_MORE,null);
                    log.d("TAG","数据查询失败"+e.toString());
                }
            }
        });
    }

    @Override
    public void doRefeshData(OldThing oldThing) {
        BmobQuery<OldThing> query = new BmobQuery<>();
        String start = oldThing.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.include("author");
        query.order("-createdAt");
        query.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
        query.findObjects(new FindListener<OldThing>() {
            @Override
            public void done(List<OldThing> list, BmobException e) {
                if(e == null){
                    if (list.size()>1){
                        list.remove(0);
                        mIOldthing.onGetResultData(true,TypeGetData.REFRESH,list);
                    }else{
                        log.d("TAG","刷新数据成功，但是没有添加数据");
                        mIOldthing.onGetResultData(true,TypeGetData.REFRESH,null);
                    }
                }else{
                    mIOldthing.onGetResultData(false,TypeGetData.REFRESH,null);
                }
            }
        });
    }
}
