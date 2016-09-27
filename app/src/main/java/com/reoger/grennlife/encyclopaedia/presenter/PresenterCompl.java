package com.reoger.grennlife.encyclopaedia.presenter;

import android.util.Log;

import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zimmerman on 2016/9/19.
 */
public class PresenterCompl implements com.reoger.grennlife.encyclopaedia.presenter.IPresenter {

    private String TAG = "tag";

    /**
     * 这个方法内测调用，用来创建对应对象的数据表，只要创建了数据表以后就不用调用了，若是数据表误删，
     * 则调用此方法创建数据表
     */
    @Override
    public void doInitDBForJavaBean() {

        EncyclopaediaBean mBeanItem = new EncyclopaediaBean("init","init","init","init");
        mBeanItem.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Log.d(TAG,"添加数据成功，返回objectId为："+objectId);
                }else{
//                    toast("创建数据失败：" + e.getMessage());
                    Log.d(TAG,"创建数据失败：" + e.getMessage());
                }
            }
        });
    }

}
