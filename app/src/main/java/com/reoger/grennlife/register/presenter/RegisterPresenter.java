package com.reoger.grennlife.register.presenter;

import android.util.Log;

import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.loginMVP.presenter.LoginPresenterCompl;
import com.reoger.grennlife.register.view.IRegisterView;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 24540 on 2016/9/10.
 */
public class RegisterPresenter implements IRegisterPresenter {
    private IRegisterView mIRegisterView;
    private BmobUser mUser;
    private LoginPresenterCompl mloginPresenterCompl;

    public RegisterPresenter(IRegisterView mIRegisterView) {
        this.mIRegisterView = mIRegisterView;
        mloginPresenterCompl = new LoginPresenterCompl();
        mUser = new BmobUser();
    }

    @Override
    public void doClear() {
        mIRegisterView.clear();
    }

    @Override
    public void doGetCheckMSMCode(String num) {
        BmobSMS.requestSMSCode(num, "greenLife", new QueryListener<Integer>() {

            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    Log.d("TAG", "验证码发送成功 " + integer);
                    mIRegisterView.getMSMCodeResult(true, integer.toString());

                } else {
                    Log.d("TAG", "验证码发送failed " + integer);
                    mIRegisterView.getMSMCodeResult(false, e.toString());
                }
            }
        });
    }

    @Override
    public void doRegister(String username, String num, String code, String password) {
        mUser.setMobilePhoneNumber(num);
        mUser.setUsername(username);
        mUser.setPassword(password);
//        mUser.signOrLoginByMobilePhone(num, code, new LogInListener<UserMode>() {
//            @Override
//            public void done(UserMode userMode, BmobException e) {
//                if (e == null) {
//                    Log.d("TAG", "注册成功 " + userMode);
//                    mIRegisterView.regsiterResult(true, null);
//
//                } else {
//                    Log.d("TAG", "注册失败 " + userMode);
//                    mIRegisterView.regsiterResult(false, e.toString());
//                }
//            }
//        });
        mUser.signOrLogin(code, new SaveListener<UserMode>() {
            @Override
            public void done(UserMode userMode, BmobException e) {
                if(e == null){
                    Log.d("TAG","注册成功 "+userMode);
                  mIRegisterView.regsiterResult(true,null);

                }else{
                    Log.d("TAG","注册失败 "+userMode);
                    mIRegisterView.regsiterResult(false,e.toString());
                }
            }
        });
    }


}
