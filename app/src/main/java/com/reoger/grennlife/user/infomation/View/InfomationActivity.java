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
import com.reoger.grennlife.utils.toast;

import cn.bmob.v3.BmobUser;


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
    private EditText mEditReduce;

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
        infomationPresenter.doUpdataUserInfo();
        userMode = BmobUser.getCurrentUser(UserMode.class);
        //这里还是需要先去查询一下数据

        mUsername.setText(userMode.getUsername().toString());
        mPhoneNum.setText(userMode.getMobilePhoneNumber().toString());
        if(userMode.getState()==null)return;
        mReallyName.setText(userMode.getReallyName().toString());
        mID.setText(userMode.getId().toString());
        mLocation.setText(userMode.getLocations());
        mState.setText(States[userMode.getState()]);
        mEditReduce.setText(userMode.getIntroduction());

        if(userMode.getState()==1||userMode.getState()==2){
            setEdit(false);
        }else if(userMode.getState()==3){
            setEdit(true);
        }
    }

    private void initEvent() {
        mAuthentication.setOnClickListener(this);

    }

    private void initView() {
        mUsername = (TextView) findViewById(R.id.user_information_username);
        mPhoneNum = (TextView) findViewById(R.id.user_information_phone_num);
        mID = (EditText) findViewById(R.id.user_information_Id);
        mReallyName = (EditText) findViewById(R.id.user_information_username_true);
        mID = (EditText)findViewById(R.id.user_information_Id);
        mLocation = (EditText) findViewById(R.id.user_information_location);
        mAuthentication = (Button) findViewById(R.id.user_information_authentication);
        mState = (TextView) findViewById(R.id.user_information_status);
        mEditReduce = (EditText) findViewById(R.id.user_information_reduce);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_information_authentication://认证
                String name = mReallyName.getText().toString();
                String Id = mID.getText().toString();
                String introducation = mEditReduce.getText().toString();
                String location = mLocation.getText().toString();
                if("".equals(name)||"".equals(Id)||"".equals(location)){
                    new toast(this,"需要将所有的信息填写完整");
                    return;
                }
                infomationPresenter.doAuthentication(name,Id,location,introducation);
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

    @Override
    public void onGetUpdataUserInfo(boolean flag, String Code) {
        if(flag){
                new toast(this,"修改成功");
        }else{
            new toast(this,"修改失败"+Code);
        }
        finish();
    }

    @Override
    public void onGetUserMode(boolean flag, UserMode user) {
        if(flag){//数据更新成功
            log.d("TTT","数据查询陈宫1234567*-*-*-*89-------------++++++++");
            mReallyName.setText(user.getReallyName().toString());
            mID.setText(user.getId().toString());
            mLocation.setText(user.getLocations());
            mState.setText(States[user.getState()]);
            if(user.getState() == 1||user.getState()==2){
                setEdit(false);
            }else{
                setEdit(true);
            }
        }else{
            log.d("TAG","数据更新失败");
        }
    }

    private void setEdit(boolean flag){
        mReallyName.setEnabled(flag);
        mID.setEnabled(flag);
        mLocation.setEnabled(flag);
        mAuthentication.setEnabled(flag);
        mEditReduce.setEnabled(flag);
    }
}
