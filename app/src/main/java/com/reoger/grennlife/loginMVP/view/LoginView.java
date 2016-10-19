package com.reoger.grennlife.loginMVP.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reoger.grennlife.MainProject.view.MainActivity;
import com.reoger.grennlife.R;
import com.reoger.grennlife.loginMVP.presenter.ILoginPresenter;
import com.reoger.grennlife.loginMVP.presenter.LoginPresenterCompl;

/**
 * Created by 24540 on 2016/9/10.
 */
public class LoginView extends AppCompatActivity implements ILoginViw, View.OnClickListener {

    public final static String REMEMBER_PASSWORD = "remember_password";
    public final static String ACCOUNT = "account";
    public final static String PASSWORD = "password";


    private ILoginPresenter mILoginPresenter;

    //实现记住密码功能用
    private SharedPreferences mPref;
    private boolean mIsRemember;
    private SharedPreferences.Editor mPasswordEditor;

    private Button mLoginLoad;
    private EditText mNum;
    private EditText mPassword;
    private TextView mRegister;
    private TextView mForgetPassword;

    private CheckBox mLoginRemember;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_main);

        mILoginPresenter = new LoginPresenterCompl(this,this);
        initView();
        //判断是否记住密码，若是则填好账号密码
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        mIsRemember = mPref.getBoolean(REMEMBER_PASSWORD, false);
        if (mIsRemember) {
            String account = mPref.getString(ACCOUNT, "");
            String password = mPref.getString(PASSWORD, "");
            mNum.setText(account);
            mPassword.setText(password);
            mLoginRemember.setChecked(true);
        }


        mLoginLoad.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
    }


//    private void initSharedSDK() {
//        ShareSDK.initSDK(this,"你的应用在Sharesdk注册时返回的AppKey");
//        HashMap<String,Object> hashMap = new HashMap<String, Object>();
//        hashMap.put("Id","1");
//        hashMap.put("SortId","1");
//        hashMap.put("AppKey","568898243");
//        hashMap.put("AppSecret","38a4f8204cc784f81f9f0daaf31e02e3");
//        hashMap.put("RedirectUrl","http://www.sharesdk.cn");
//        hashMap.put("ShareByAppClient","true");
//        hashMap.put("Enable","true");
//        ShareSDK.setPlatformDevInfo(SinaWeibo.NAME,hashMap);
//    }
    @Override
    public void onClear() {

    }

    @Override
    public void onLoginResult(Boolean result) {
        if (result) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            mILoginPresenter.doComeMainActivity(this);
        } else {
            Toast.makeText(this, "用户名或密码不正确!", Toast.LENGTH_SHORT).show();
        }
    }


    private void initView() {
        mForgetPassword = (TextView) findViewById(R.id.login_forgetpassword);
        mLoginLoad = (Button) findViewById(R.id.login_load);
        mNum = (EditText) findViewById(R.id.login_num);
        mPassword = (EditText) findViewById(R.id.login_password);
        mRegister = (TextView) findViewById(R.id.login_register);
        mLoginRemember = (CheckBox) findViewById(R.id.login_remember);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录按钮
            case R.id.login_load:
                //实行记住密码逻辑
                mILoginPresenter.doRememberPasswordWhileLogin(mNum.getText().toString(),
                        mPassword.getText().toString(),mLoginRemember.isChecked());
                //登录
                mILoginPresenter.doLogin(mNum.getText().toString(), mPassword.getText().toString());
                break;
            //注册
            case R.id.login_register:
                mILoginPresenter.doRegister(this);
                break;
            //忘记密码
            case R.id.login_forgetpassword:
                Log.e("TAG","click forget password");
                mILoginPresenter.doForgetPassword(this);
                break;
        }
    }



    /**
     * 跳过，后面可能需要删除
     */


    public void skip(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
