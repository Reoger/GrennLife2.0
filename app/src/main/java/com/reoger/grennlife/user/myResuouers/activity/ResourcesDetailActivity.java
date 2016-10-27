package com.reoger.grennlife.user.myResuouers.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.utils.log;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 24540 on 2016/10/17.
 */
public class ResourcesDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mName;
    private TextView mContent;
    private TextView mPhone;
    private TextView mLocation;
    private TextView mTime;
    private TextView mState;
    private GridLayout mAddPhone;
    private ImageButton mBack;
    private Switch mSwicth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myresources_detail);
        initView();
        initData();
    }

    private void initData() {
        final OldThing oldThing = (OldThing) getIntent().getSerializableExtra(MyResources.OLDTHING);
        mBack.setOnClickListener(this);
        mName.setText("名字是"+oldThing.getTitle());
        mTime.setText("时间是"+oldThing.getCreatedAt());
        mPhone.setText(""+oldThing.getNum());
        mLocation.setText(oldThing.getLocations());
        mContent.setText("详细描述是"+oldThing.getContent());
        if(oldThing.isAvailable()){
            mState.setText("领取状态："+"可领取");
            mSwicth.setChecked(true);
        }else{
            mState.setText("领取状态："+"已领取");
            mSwicth.setChecked(false);
        }
        mSwicth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OldThing old = new OldThing();
                old.setObjectId(oldThing.getObjectId());
                if(isChecked){
                    old.setAvailable(true);//可领取的
                }else{
                    old.setAvailable(false);
                }
                old.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            log.d("TAG","数据更新成功222");
                        }else{
                            log.d("TAG","数据更新失败33");
                        }
                    }
                });
            }
        });
        if(oldThing.getImageUrl()!= null){
            //还有图片需要加载
            log.d("TAG","ppppp"+oldThing.getImageUrl());
        }
    }

    private void initView() {
        mSwicth = (Switch) findViewById(R.id.switch1);
        mName = (TextView) findViewById(R.id.resources_detail_name);
        mContent = (TextView) findViewById(R.id.resources_detail_content);
        mPhone = (TextView) findViewById(R.id.resources_detail_phone);
        mLocation = (TextView) findViewById(R.id.resources_detail_location);
        mTime = (TextView) findViewById(R.id.resources_detail_time);
        mState = (TextView) findViewById(R.id.resources_detail_state);
        mAddPhone = (GridLayout) findViewById(R.id.resources_detail_add_photo);
        mBack = (ImageButton) findViewById(R.id.all__return);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.all__return:
                finish();
                break;
        }
    }
}
