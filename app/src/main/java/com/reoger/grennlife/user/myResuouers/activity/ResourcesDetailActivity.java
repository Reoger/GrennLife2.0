package com.reoger.grennlife.user.myResuouers.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.utils.log;

/**
 * Created by 24540 on 2016/10/17.
 */
public class ResourcesDetailActivity extends AppCompatActivity{

    private TextView mName;
    private TextView mContent;
    private TextView mPhone;
    private TextView mLocation;
    private TextView mTime;
    private TextView mState;
    private GridLayout mAddPhone;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myresources_detail);
        initView();
        initData();
    }

    private void initData() {
        OldThing oldThing = (OldThing) getIntent().getSerializableExtra(MyResources.OLDTHING);
        mName.setText("名字是"+oldThing.getTitle());
        mTime.setText("时间是"+oldThing.getCreatedAt());
        mContent.setText("详细描述是"+oldThing.getContent());
        if(oldThing.getImageUrl()!= null){
            //还有图片需要加载
            log.d("TAG","ppppp"+oldThing.getImageUrl());
        }
    }

    private void initView() {
        mName = (TextView) findViewById(R.id.resources_detail_name);
        mContent = (TextView) findViewById(R.id.resources_detail_content);
        mPhone = (TextView) findViewById(R.id.resources_detail_phone);
        mLocation = (TextView) findViewById(R.id.resources_detail_location);
        mTime = (TextView) findViewById(R.id.resources_detail_time);
        mState = (TextView) findViewById(R.id.resources_detail_state);
        mAddPhone = (GridLayout) findViewById(R.id.resources_detail_add_photo);
    }
}
