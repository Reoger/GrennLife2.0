package com.reoger.grennlife.TitleLayout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.reoger.grennlife.R;

/**
 * Created by 24540 on 2016/9/27.
 */
public class TitleLayout_add_dynamic extends LinearLayout {
    public TitleLayout_add_dynamic(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_add_dynamic, this);
        ImageButton titleBack = (ImageButton) findViewById(R.id.title_add_dynamic_button);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
}
