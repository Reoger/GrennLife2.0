package com.reoger.grennlife.user.setting.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        init();
    }

    private void init() {
        mSettingCompl = new SettingCompl(this);
        //设置按钮条
        mExitLayout = (LinearLayout) findViewById(R.id.setting_layout_logout);
        //联系我们按钮条
        mContactUsLayout = (LinearLayout) findViewById(R.id.setting_layout_contact_us);

        mContactUsLayout.setOnClickListener(this);
        mExitLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_layout_logout:
                mSettingCompl.logOut();
                break;
            case R.id.setting_layout_contact_us:
                startActivity(new Intent(this,ContactUsView.class));
                break;

        }
    }
}
