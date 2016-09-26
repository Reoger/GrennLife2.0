package com.reoger.grennlife.ResetPassword.presenter;

import android.content.Context;

/**
 * Created by Zimmerman on 2016/9/13.
 */
public interface IResetPasswordPresenter {
    //重置密码
//    获取充值密码手机验证码
    void doGetMSMCodeForReset(String num);

    //确认发送重置请求
    void doSendResetRequest(String newPassword, String SMSCode);
}
