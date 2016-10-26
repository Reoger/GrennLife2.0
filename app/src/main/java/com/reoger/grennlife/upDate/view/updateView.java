package com.reoger.grennlife.upDate.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.upDate.presenter.IUpdatePresenter;
import com.reoger.grennlife.upDate.presenter.PresenterCompl;
import com.reoger.grennlife.utils.CustomApplication;
import com.reoger.grennlife.utils.SwitchButton;
import com.reoger.grennlife.utils.toast;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by Zimmerman on 2016/10/25.
 */
public class updateView extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    private Button mUpdate;
    private SwitchButton mNetworkSwitchBtn;
    private SwitchButton mAutoSwitchBtn;

    private SharedPreferences mPref;

    private IUpdatePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_update);
        init();
    }

    private void init() {
        mUpdate = (Button) findViewById(R.id.setting_update_action_btn);
        mNetworkSwitchBtn = (SwitchButton) findViewById(R.id.setting_update_switch_network);
        mAutoSwitchBtn = (SwitchButton) findViewById(R.id.setting_update_auto_update);
        //取出记住选择状态
        rememberSwitchBtnState();

        mUpdate.setOnClickListener(this);
        mNetworkSwitchBtn.setOnCheckedChangeListener(this);
        mAutoSwitchBtn.setOnCheckedChangeListener(this);
    }

    private void rememberSwitchBtnState() {
        mPresenter = new PresenterCompl();
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        mNetworkSwitchBtn.setChecked(mPref.getBoolean(PresenterCompl.NET_UPDATE_IS_CHECK,false));
        mAutoSwitchBtn.setChecked(mPref.getBoolean(PresenterCompl.NET_UPDATE_AUTO_IS_CHECK,true));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_update_action_btn:
                new toast(this, "检查更新...");
                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        // TODO Auto-generated method stub
                        if (updateStatus == UpdateStatus.Yes) {//版本有更新

                        } else if (updateStatus == UpdateStatus.No) {
                            Toast.makeText(updateView.this, "版本无更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
//                            Toast.makeText(updateView.this, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.IGNORED) {
//                            Toast.makeText(updateView.this, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
//                            Toast.makeText(updateView.this, "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.TimeOut) {
//                            Toast.makeText(updateView.this, "查询出错或查询超时", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                BmobUpdateAgent.forceUpdate(this);
                break;
        }
    }

    //滑动开关监听事件
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.setting_update_switch_network:
                BmobUpdateAgent.setUpdateOnlyWifi(isChecked);
                //记住状态
                mPresenter.rememberNetWorkState(isChecked);
                break;
            case R.id.setting_update_auto_update:
                mPresenter.rememberAutoUpdate(isChecked);
                break;
        }
    }
}
