package com.reoger.grennlife.MainProject.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reoger.grennlife.R;

/**
 * Created by 24540 on 2016/10/26.
 */
public class ShowPicView extends Activity implements View.OnTouchListener{
    private ImageView mShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_pic);
        mShow = (ImageView) findViewById(R.id.show_pic);
        Intent i = getIntent();
        String pic = i.getStringExtra("Pic");
        Glide.with(this).load(pic).into(mShow);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finish();
        return false;
    }
}
