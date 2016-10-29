package com.reoger.grennlife.user.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.reoger.grennlife.R;

/**
 * Created by Zimmerman on 2016/10/19.
 */
public class ContactUsView extends AppCompatActivity {
    private ImageButton mBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_contact_us);
        mBack = (ImageButton) findViewById(R.id.toolbar_button1);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
