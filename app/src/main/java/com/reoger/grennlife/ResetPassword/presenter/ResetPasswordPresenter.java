package com.reoger.grennlife.ResetPassword.presenter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.reoger.grennlife.utils.CustomApplication;
import com.reoger.grennlife.utils.toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Zimmerman on 2016/9/13.
 */
public class ResetPasswordPresenter implements IResetPasswordPresenter {

    private Context mContext;

    public ResetPasswordPresenter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     *
     * @param num    手机号
     */
    @Override
    public void doGetMSMCodeForReset(String num) {

        BmobSMS.requestSMSCode(num, "GreenLife", new QueryListener<Integer>() {

                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        if(ex==null){//验证码发送成功
                            Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                        } else {
                            Log.i("smile", "短信失败 原因："+ex.toString());
                        }
                    }
                });
    }

    @Override
    public void doResetPwByCode(String SMSCode, String newPW) {
        BmobUser.resetPasswordBySMSCode(SMSCode, newPW, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    new toast(mContext,"充值成功");
                    ((AppCompatActivity)mContext).finish();
                    Log.d("qqe","重置成功");
                } else {
                    Log.d("qqe","充值失败：code:"+e.getErrorCode()+"meg:"+e.getMessage());
                }
            }
        });
    }

}
