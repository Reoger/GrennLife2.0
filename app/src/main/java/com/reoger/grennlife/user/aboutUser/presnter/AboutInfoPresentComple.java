package com.reoger.grennlife.user.aboutUser.presnter;

import android.content.Context;

import com.reoger.grennlife.MainProject.model.Comment;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.user.aboutUser.view.IAboutActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 24540 on 2016/10/24.
 */
public class AboutInfoPresentComple implements IAboutInfoPresent{
    private Context mContext;
    private IAboutActivity mIAbout;

    public AboutInfoPresentComple(Context mContext, IAboutActivity mIAbout) {
        this.mContext = mContext;
        this.mIAbout = mIAbout;
    }

    @Override
    public void doGetInVailatalData() {
        BmobQuery<Comment> query = new BmobQuery<>();
        UserMode userMode = BmobUser.getCurrentUser(UserMode.class);
        query.addWhereEqualTo("postAuthor",userMode.getUsername());//查询与我相关需要添加作者
        query.setLimit(10);
        query.include("dynamic");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e == null){
                    mIAbout.onResultGetData(true,list);
                }else{
                    mIAbout.onResultGetData(false,null);
                }
            }
        });
    }
}
