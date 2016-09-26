package com.reoger.grennlife.loginMVP.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.reoger.grennlife.ResetPassword.view.ResetPasswordView;
import com.reoger.grennlife.loginMVP.view.ILoginViw;
import com.reoger.grennlife.loginMVP.view.LoginView;
import com.reoger.grennlife.utils.CustomApplication;
import com.reoger.grennlife.utils.log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 24540 on 2016/9/10.
 */


public class LoginPresenterCompl implements ILoginPresenter {

    private final static String TAG = "com.reoger.grennlife.loginMVP.presenter";

    private ILoginViw mILoginView;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mPasswordEditor;
    public LoginPresenterCompl() {

        //debug
        mILoginView = new LoginView();
    }

    public LoginPresenterCompl(ILoginViw mILoginView) {
        this.mILoginView = mILoginView;
    }

    @Override
    public void doLogin(String name, String password) {
        BmobUser user = new BmobUser();
        user.setUsername(name);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                  mILoginView.onLoginResult(true);
                    log.d(TAG,"登陆成功");
                } else {
                    Log.d("TAG","登陆失败~"+"321");
                    mILoginView.onLoginResult(false);
                }
            }
        });
    }

    @Override
    public void doRegister(Context context) {
        context.startActivity(new Intent(context,com.reoger.grennlife.register.view.RegisterView.class));
    }

    @Override
    public void doRememberPasswordWhileLogin(String userName,String password,boolean isChecked) {
        //若勾选了记住密码，则执行记住密码的逻辑
        mPref = PreferenceManager.getDefaultSharedPreferences(CustomApplication.getContext());
        mPasswordEditor = mPref.edit();
        if (isChecked) {
            mPasswordEditor.putBoolean(LoginView.REMEMBER_PASSWORD, true);
            mPasswordEditor.putString(LoginView.ACCOUNT, userName);
            mPasswordEditor.putString(LoginView.PASSWORD, password);
        } else {
            mPasswordEditor.clear();
        }
        mPasswordEditor.commit();
    }

    @Override
    public void doForgetPassword(Context context) {
        Intent intent = new Intent(context, ResetPasswordView.class);
        context.startActivity(intent);
    }

    @Override
    public void doComeMainActivity(Context context) {
        context.startActivity(new Intent(context,com.reoger.grennlife.MainProject.view.MainActivity.class));
    }
}
