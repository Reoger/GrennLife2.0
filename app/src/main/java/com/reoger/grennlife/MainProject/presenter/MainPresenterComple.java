package com.reoger.grennlife.MainProject.presenter;

import android.content.Context;
import android.content.Intent;

import com.reoger.grennlife.MainProject.view.IMainActivity;
import com.reoger.grennlife.Recycle.view.RecycleView;
import com.reoger.grennlife.monitoring.view.EnvironmentalMonitoring;

/**
 * Created by 24540 on 2016/9/11.
 */
public class MainPresenterComple implements IMainPresenter {

    public static final int MONITORING = 1;
    public static final int RECYCLE = 2;


    private IMainActivity IMainActivity;

    public MainPresenterComple(com.reoger.grennlife.MainProject.view.IMainActivity IMainActivity) {
        this.IMainActivity = IMainActivity;
    }

    /**
     * 跳转到环境保护界面
     * @param context
     */
    @Override
    public void doComeActivity(Context context, int num) {
        Intent intent = null;
        switch(num){
            case MONITORING:
                intent = new Intent(context,EnvironmentalMonitoring.class);
                break;
            case RECYCLE:
                intent = new Intent(context, RecycleView.class);
        }
        context.startActivity(intent);
    }
}
