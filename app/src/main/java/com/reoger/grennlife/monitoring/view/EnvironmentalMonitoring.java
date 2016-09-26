package com.reoger.grennlife.monitoring.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.monitoring.presenter.IMonitoringPresenter;
import com.reoger.grennlife.monitoring.presenter.MonitoringPresenterCompl;
import com.reoger.grennlife.utils.GetPhoto;
import com.reoger.grennlife.utils.log;

/**
 * Created by 24540 on 2016/9/11.
 */
public class EnvironmentalMonitoring extends AppCompatActivity implements IEnvironmentalMonitoring, View.OnClickListener {


    private static final String TAG = "EnvironmentalMonitoring";
    private IMonitoringPresenter mIMonitoringPresenter;



    private EditText mLocation;
    private EditText mTitle;
    private EditText mContent;
    private EditText mNum;


    private ImageButton mTakePhone;

    private ImageView mPicture;

    private Button mMonitoring;
    private Button mSelectPhoto;
    private Button mClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_monitoring_main);
        initView();
        initEvents();
    }

    private void initEvents() {
        mIMonitoringPresenter = new MonitoringPresenterCompl(this,EnvironmentalMonitoring.this);
        mIMonitoringPresenter.doGetCurrentLocation(this);

        mTakePhone.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mMonitoring.setOnClickListener(this);
        mSelectPhoto.setOnClickListener(this);

    }

    private void initView() {
        mLocation = (EditText) findViewById(R.id.monitoring_location);
        mTitle = (EditText) findViewById(R.id.monitoring_title);
        mContent = (EditText) findViewById(R.id.monitoring_content);
        mNum = (EditText) findViewById(R.id.monitoring_num);
        mSelectPhoto = (Button) findViewById(R.id.monitoring_select_photo);

        mPicture = (ImageView) findViewById(R.id.monitoring_photo);
        mTakePhone = (ImageButton) findViewById(R.id.monitoring_take_photo);

        mMonitoring = (Button) findViewById(R.id.monitoring_button);
        mClear = (Button)findViewById(R.id.monitoring_clear);
    }

    @Override
    public void onClear() {
        mContent.setText("");
        mTitle.setText("");
        mNum.setText("");
        mLocation.setText("");
    }

    /**
     * 显示当前位置信息
     *
     * @param location
     */
    @Override
    public void onGetCurrentLocation(final String location) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLocation.setText(location);
            }
        });

    }

    @Override
    public void onShowPicture(Bitmap bitmap) {
        log.d(TAG,"设置图片资源");
        mPicture.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monitoring_take_photo://拍照
                mIMonitoringPresenter.doTakePhone(this);
                break;
            case R.id.monitoring_select_photo://从相册中选择
                mIMonitoringPresenter.doGetPhoto(this,GetPhoto.ACTIVITY_RESULT_ALBUM);
                break;
            case R.id.monitoring_button://上传举报信息
                String title = mTitle.getText().toString();
                String content = mContent.getText().toString();
                String num = mNum.getText().toString();
                String location = mLocation.getText().toString();
             // Bitmap bitmap = BitmapFactory.decodeResource(this,,mPicture);
               Bitmap bitmap = mPicture.getDrawingCache();
                log.d("YYY",title+content+num+location+"SDd");
                mIMonitoringPresenter.doUploadMonitoringInfo(title,content,num,location,"123");
                break;
            case R.id.monitoring_clear:
                mIMonitoringPresenter.doClear();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch ((requestCode)) {
                case GetPhoto.ACTIVITY_RESULT_CAMERA://拍照
                   mIMonitoringPresenter.doCutPircture(this);
                    break;
                case GetPhoto.ACTIVITY_RESULT_ALBUM://从相册中选择
                    mIMonitoringPresenter.doGetBitmapPicture(this);
                    break;
            }
        }else{
            mIMonitoringPresenter.doDeletePhoto();
        }
    }
}
