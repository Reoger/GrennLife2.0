package com.reoger.grennlife.loginMVP.presenter;

import android.content.Context;

/**
 * Created by 24540 on 2016/9/10.
 */
public interface ILoginPresenter {
    /**
     * 登录
     */
    void doLogin(String name, String password);

    void doRegister(Context context);

    void doComeMainActivity(Context context);

    void doRememberPasswordWhileLogin(String userName,String password,boolean isChecked);

    void doForgetPassword(Context context);
}
