package com.reoger.grennlife.ResetPassword.presenter;


import android.util.Log;




import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Zimmerman on 2016/9/13.
 */
public class ResetPasswordPresenter implements IResetPasswordPresenter {

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
    public void doSendResetRequest(String newPassword, String SMSCode) {

            BmobUser.resetPasswordBySMSCode(SMSCode, newPassword, new UpdateListener() {
                @Override
                public void done(BmobException ex) {
                    if(ex==null){
                        Log.i("smile", "密码重置成功");
                    }else{
//                        Toast.makeText(MyApplication.getContext(),"重置失败："+ex.getLocalizedMessage(),
//                                Toast.LENGTH_SHORT).show();
                        Log.i("smile", "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                    }
                }
            });
    }
}
