package com.reoger.grennlife.TitleLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.reoger.grennlife.R;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class TitleLayout_home extends LinearLayout {
    public TitleLayout_home(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_title, this);
        ImageButton remind = (ImageButton)findViewById(R.id.system_remind);
        remind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"消息提醒",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
