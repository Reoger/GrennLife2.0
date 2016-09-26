package com.reoger.grennlife.ResetPassword.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.ResetPassword.presenter.IResetPasswordPresenter;
import com.reoger.grennlife.ResetPassword.presenter.ResetPasswordPresenter;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Zimmerman on 2016/9/13.
 */
public class ResetPasswordView extends AppCompatActivity implements IResetPasswordView, View.OnClickListener {

    private IResetPasswordPresenter mIResetPasswordPresenter;

    private EditText mResetPhoneNum;
    private EditText mResetVeryfiedCode;
    private EditText mPassword;
    private EditText mPasswordAgain;
    //提交按钮
    private ImageButton mExecuteBtn;
    //请求验证码按钮
    private ImageButton mGetVeryfiedCodeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reset_password_view);

        initAttr();
        initView();

        mExecuteBtn.setOnClickListener(this);
        mGetVeryfiedCodeBtn.setOnClickListener(this);
    }

    private void initAttr() {
        mIResetPasswordPresenter = new ResetPasswordPresenter();
    }

    private void initView() {
        mPassword = (EditText) findViewById(R.id.reset_new_password);
        mPasswordAgain = (EditText) findViewById(R.id.reset_second_password);
        mResetPhoneNum = (EditText) findViewById(R.id.reset_phonenum);
        mResetVeryfiedCode = (EditText) findViewById(R.id.reset_verified_code);
        //获取验证码按钮
        mGetVeryfiedCodeBtn = (ImageButton) findViewById(R.id.reset_get_sms_code_btn);
        mExecuteBtn = (ImageButton) findViewById(R.id.reset_reset_password_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_get_sms_code_btn:
                //获取验证码的逻辑
                Log.d("TAG reset",""+mResetPhoneNum);
                mIResetPasswordPresenter.doGetMSMCodeForReset(mResetPhoneNum.getText().toString());
                break;
            case R.id.reset_reset_password_btn:
                //执行重置密码逻辑
                if (mPassword.getText().toString().equals(mPasswordAgain.getText().toString())) {
                    //验证两次输入密码相同
                    mIResetPasswordPresenter.doSendResetRequest(mPassword.getText().toString(),
                            mResetVeryfiedCode.toString());
                    break;
                } else {
                    //两次密码输入不同,要求重新输入
                    Toast.makeText(this,"两次输入密码不同，请重新输入密码",Toast.LENGTH_SHORT).show();
                    mPassword.setText("");
                    mPasswordAgain.setText("");

                }
        }


    }

}
