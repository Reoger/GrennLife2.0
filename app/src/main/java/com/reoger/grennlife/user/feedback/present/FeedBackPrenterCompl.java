package com.reoger.grennlife.user.feedback.present;

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

    public FeedBackPrenterCompl(IFeedBack mIFeedBack, Context mContext) {
        this.mIFeedBack = mIFeedBack;
        this.mContext = mContext;
    }

    @Override
    public void doOnPuhlish(String content, String email) {
        Feedback item = new Feedback();
        item.setContent(content);
        item.setEmail(email);
        item.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    mIFeedBack.onResultForPuhlish(true,null);
                }else{
                    mIFeedBack.onResultForPuhlish(false,e.toString());
                }
            }
        });
    }
}
