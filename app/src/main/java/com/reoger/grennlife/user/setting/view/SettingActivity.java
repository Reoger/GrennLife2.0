package com.reoger.grennlife.user.setting.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.reoger.grennlife.R;
import com.reoger.grennlife.user.setting.presenter.ISettingView;
import com.reoger.grennlife.user.setting.presenter.SettingCompl;

/**
 * Created by 24540 on 2016/10/19.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private ISettingView mSettingCompl;

    private LinearLayout mExitLayout;
    private LinearLayout mContactUsLayout;
    private LinearLayout mSharedLayout;
    private LinearLayout mPrivacyLayout;

    private ImageButton mToolBarBackBtn;

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

        mPrivacyLayout.setOnClickListener(this);
        mContactUsLayout.setOnClickListener(this);
        mSharedLayout.setOnClickListener(this);
        mExitLayout.setOnClickListener(this);
        mToolBarBackBtn.setOnClickListener(this);
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
        }
    }
}
