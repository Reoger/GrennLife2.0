package com.reoger.grennlife.guide.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.reoger.grennlife.MainProject.view.MainActivity;
import com.reoger.grennlife.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 24540 on 2016/10/17.
 */
public class WelcomeActivity extends AppCompatActivity {
    private boolean mISFirstUse;
    private TextView mTime;
    Timer timer = new Timer();
    private int recLen = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome);
        mTime = (TextView) findViewById(R.id.time_task);
        timer.schedule(task, 1000, 1000);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    mTime.setText("" + recLen + "S");
                    if (recLen < 1) {
                        timer.cancel();
                        mTime.setVisibility(View.GONE);
                        SharedPreferences preferences = getSharedPreferences("mISFirstUse", MODE_WORLD_READABLE);
                        mISFirstUse = preferences.getBoolean("mISFirstUse", true);
                        /**
                         *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
                         */
                        if (mISFirstUse) {
                            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                        } else {
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        }
                        finish();

                        //实例化Editor对象
                        SharedPreferences.Editor editor = preferences.edit();
                        //存入数据
                        editor.putBoolean("mISFirstUse", false);
                        //提交修改
                        editor.commit();
                    }
                }
            });
        }
    };


}
