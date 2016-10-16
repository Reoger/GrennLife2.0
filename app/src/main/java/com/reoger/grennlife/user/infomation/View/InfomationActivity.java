package com.reoger.grennlife.user.infomation.View;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.user.infomation.presenter.InfomationPresenter;
import com.reoger.grennlife.user.infomation.presenter.InfomationPresenterCompl;
import com.reoger.grennlife.utils.log;

import cn.bmob.v3.BmobUser;

import static com.reoger.grennlife.R.id.user_information_username;

/**
 * Created by 24540 on 2016/10/11.
 */
public class InfomationActivity extends AppCompatActivity implements View.OnClickListener,IInfomationView{

    private TextView mUsername;
    private TextView mPhoneNum;
    private EditText mReallyName;
    private EditText mID;
    private EditText mLocation;
    private Button mAuthentication;
    private TextView mState;

    private UserMode userMode;
    private InfomationPresenter infomationPresenter;

    private static final String[] States={"","正在审核","审核通过","审核不通过"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_information);
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        infomationPresenter = new InfomationPresenterCompl(this,this);
        infomationPresenter.doGetCurrentLocation(this);
         userMode = BmobUser.getCurrentUser(UserMode.class);
        mUsername.setText(userMode.getUsername().toString());
        mPhoneNum.setText(userMode.getMobilePhoneNumber().toString());
        if(userMode.getState()==null)return;
        if(userMode.getState()==1||userMode.getState()==2){
            mReallyName.setText(userMode.getReallyName().toString());
            mID.setText(userMode.getId().toString());
            mLocation.setText(userMode.getLocations());
            mReallyName.setEnabled(false);//设置为不可编写
            mID.setEnabled(false);
            mLocation.setEnabled(false);
            mAuthentication.setEnabled(false);
            mState.setText(States[userMode.getState()]);
        }else if(userMode.getState()==3){
            mReallyName.setEnabled(true);
            mID.setEnabled(true);
            mLocation.setEnabled(true);
            mAuthentication.setEnabled(true);
            mState.setText(States[userMode.getState()]);
        }
    }

    private void initEvent() {
        mAuthentication.setOnClickListener(this);

    }

    private void initView() {
        mUsername = (TextView) findViewById(user_information_username);
        mPhoneNum = (TextView) findViewById(R.id.user_information_phone_num);
        mID = (EditText) findViewById(R.id.user_information_Id);
        mReallyName = (EditText) findViewById(R.id.user_information_username_true);
        mID = (EditText)findViewById(R.id.user_information_Id);
        mLocation = (EditText) findViewById(R.id.user_information_location);
        mAuthentication = (Button) findViewById(R.id.user_information_authentication);
        mState = (TextView) findViewById(R.id.user_information_status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_information_authentication://认证
                String name = mReallyName.getText().toString();
                String Id = mID.getText().toString();
                String location = mLocation.getText().toString();
                infomationPresenter.doAuthentication(name,Id,location);
                break;
        }
    }

    @Override
    public void onGetLocation(boolean flay, Location location) {
            if(flay){
                log.d("Tag","location"+location.getLatitude()+"::location.getLongitude()"+location.getLongitude()+"::"+
                location.getAltitude());
            }else{
                log.d("TAg","获得位置信息失败");
            }
    }
}
