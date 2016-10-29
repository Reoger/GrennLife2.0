package com.reoger.grennlife.user.feedback.present;

import android.app.ProgressDialog;
import android.content.Context;

import com.reoger.grennlife.user.feedback.Bean.Feedback;
import com.reoger.grennlife.user.feedback.view.IFeedBack;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 24540 on 2016/10/19.
 */
public class FeedBackPrenterCompl implements IFeedBackPrenter {
    private IFeedBack mIFeedBack;
    private Context mContext;
    private ProgressDialog mProgress;

    public FeedBackPrenterCompl(IFeedBack mIFeedBack, Context mContext) {
        this.mIFeedBack = mIFeedBack;
        this.mContext = mContext;
    }

    @Override
    public void doOnPuhlish(String content, String email) {
        showPorgressDialog();
        Feedback item = new Feedback();
        item.setContent(content);
        item.setEmail(email);
        item.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    mProgress.dismiss();
                    mIFeedBack.onResultForPuhlish(true,null);
                }else{
                    mProgress.dismiss();
                    mIFeedBack.onResultForPuhlish(false,e.toString());
                }
            }
        });
    }

    private void showPorgressDialog(){
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("提交中...");
        mProgress.setTitle("waitiing...");
        mProgress.setCancelable(false);
        mProgress.show();
    }
}
