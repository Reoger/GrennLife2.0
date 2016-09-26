package com.reoger.grennlife.Recycle.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.presenter.IPublishResourcesPresenter;
import com.reoger.grennlife.Recycle.presenter.PublishResourcesPresenterCompl;
import com.reoger.grennlife.utils.toast;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.List;

/**
 * Created by 24540 on 2016/9/18.
 */
public class PublishingResourcesView extends AppCompatActivity implements View.OnClickListener,IPublishingResouresView{
    private Spinner mSpinner;
    private String[] mItems;
    private ArrayAdapter<String> adapter;

    private static final int RESULT_CODE= 0x110;

    private Button mPublisResources;

    /**类型（旧物利用、资源回收）*/
    private String mType;
    /**描述*/
    private EditText mContent;
    /**联系方式*/
    private EditText mPhoneNum;
    /**联系的地点*/
    private EditText mLocation;
    /**添加图片*/
    private ImageButton mAddPhoto;
    /**显示图片，暂时只是用于测试*/
    private ImageView mImage;

    private IPublishResourcesPresenter mIPublishResourcesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycle_publishing);

        initView();
        initData();
        setEvent();
    }

    private void setEvent() {
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        mSpinner .setAdapter(adapter);

        mAddPhoto.setOnClickListener(this);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] type = getResources().getStringArray(R.array.type);
                Toast.makeText(PublishingResourcesView.this, "你点击的是:"+type[pos], Toast.LENGTH_SHORT).show();
                mType = type[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        mPublisResources.setOnClickListener(this);
    }


    private void initView() {
        mSpinner = (Spinner) findViewById(R.id.publish_resources_type);
        mPublisResources = (Button) findViewById(R.id.publish_resources);
        mContent = (EditText) findViewById(R.id.publish_resources_content);
        mPhoneNum = (EditText) findViewById(R.id.publish_resources_num);
        mLocation = (EditText) findViewById(R.id.publish_resources_location);
        mAddPhoto = (ImageButton) findViewById(R.id.public_resources_add_photo);

        mImage = (ImageView) findViewById(R.id.public_resources_photo);

        mIPublishResourcesPresenter = new PublishResourcesPresenterCompl(PublishingResourcesView.this,this);
    }

    private void initData() {
         mItems = getResources().getStringArray(R.array.type);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_resources://发布资源
                mIPublishResourcesPresenter.doPublishResouerces(mType,mContent.getText().toString(),mLocation.getText().toString()
                        ,mPhoneNum.getText().toString());
                break;
            case R.id.public_resources_add_photo://点击添加图片
                mIPublishResourcesPresenter.doAddPhoto(RESULT_CODE);
                break;
        }
    }

    @Override
    public void onPublishResulte(boolean flag, int code) {
        if(flag){
            new toast(this,"资源发布成功");
        }else{
            new toast(this,"资源发布失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == RESULT_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            for (String path : pathList) {
                mLocation.append(path + "\n");
               // Picasso.with(this).load(path).into(mImage);

            }
        }
    }
}
