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
public class TitleLayout_moniter extends LinearLayout {
    public TitleLayout_moniter(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this);
        ImageButton titleBack = (ImageButton) findViewById(R.id.toolbar_button1);
        // ImageButton titleEdit = (ImageButton)findViewById(R.id.toolbar_button2);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
}
