package com.reoger.grennlife.register.presenter;

/**
 * Created by 24540 on 2016/9/10.
 */
public interface IRegisterPresenter {
    void doClear();//清除文本输入
    void doGetCheckMSMCode(String num);//获取手机验证码
    void doRegister(String username,String num,String code,String password);//注册
}
