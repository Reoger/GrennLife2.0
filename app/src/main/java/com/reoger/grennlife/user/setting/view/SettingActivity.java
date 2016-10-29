package com.reoger.grennlife.user.setting.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.reoger.grennlife.R;
import com.reoger.grennlife.upDate.view.updateView;
import com.reoger.grennlife.user.setting.presenter.ISettingView;
import com.reoger.grennlife.user.setting.presenter.SettingCompl;
import com.reoger.grennlife.user.setting.tools.DataCleanManager;
import com.reoger.grennlife.utils.toast;

/**
 * Created by 24540 on 2016/10/19.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private ISettingView mSettingCompl;

    private LinearLayout mExitLayout;
    private LinearLayout mContactUsLayout;
    private LinearLayout mSharedLayout;
    private LinearLayout mPrivacyLayout;
    private LinearLayout mUpdateLayout;
    private LinearLayout mClearData;

    private ImageButton mToolBarBackBtn;

    private AlertDialog alertDialog1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        init();
    }

    private void init() {
        mSettingCompl = new SettingCompl(this);
        //返回按钮
        mToolBarBackBtn = (ImageButton) findViewById(R.id.toolbar_button_return);
        //设置按钮条
        mExitLayout = (LinearLayout) findViewById(R.id.setting_layout_logout);
        //联系我们按钮条
        mContactUsLayout = (LinearLayout) findViewById(R.id.setting_layout_contact_us);
        //应用分享按钮条
        mSharedLayout = (LinearLayout) findViewById(R.id.setting_layout_share);
        //隐私条款按钮条
        mPrivacyLayout = (LinearLayout) findViewById(R.id.setting_layout_privacy);
        //更新按钮
        mUpdateLayout = (LinearLayout) findViewById(R.id.setting_layout_update);

        mClearData = (LinearLayout) findViewById(R.id.setting_layout_clear_data);

        mPrivacyLayout.setOnClickListener(this);
        mContactUsLayout.setOnClickListener(this);
        mSharedLayout.setOnClickListener(this);
        mExitLayout.setOnClickListener(this);
        mUpdateLayout.setOnClickListener(this);
        mToolBarBackBtn.setOnClickListener(this);
        mClearData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_button_return:
                this.finish();
                break;
            case R.id.setting_layout_logout:
                mSettingCompl.logOut();
                break;
            case R.id.setting_layout_contact_us:
                startActivity(new Intent(this,ContactUsView.class));
                break;
            case R.id.setting_layout_share:
                mSettingCompl.doAppShare(this);
                break;
            case R.id.setting_layout_privacy:
                startActivity(new Intent(this,PrivacyActivity.class));
                break;
            case R.id.setting_layout_update:
//                建表用↓↓
//                BmobUpdateAgent.initAppVersion();
                startActivity(new Intent(this,updateView.class));
                break;
            case R.id.setting_layout_clear_data:
                ShowDoalog();
                break;
        }
    }

    private void ShowDoalog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            builder.setMessage("共缓存了"+DataCleanManager.getTotalCacheSize(SettingActivity.this)+"数据,确定要全部清除？");
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataCleanManager.clearAllCache(SettingActivity.this);
                new toast(SettingActivity.this,"清除缓存成功");
            }
        });
        builder.setTitle("清除缓存");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }
}
