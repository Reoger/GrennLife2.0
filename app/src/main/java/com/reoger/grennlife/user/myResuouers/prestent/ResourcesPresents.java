package com.reoger.grennlife.user.myResuouers.prestent;

import android.content.Context;

import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.user.myResuouers.activity.IMyResources;
import com.reoger.grennlife.utils.log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 24540 on 2016/10/17.
 */
public class ResourcesPresents implements  IResourcesPresntens {

    private IMyResources mIM;
    private Context mComtext;

    public ResourcesPresents(IMyResources mIM, Context mComtext) {
        this.mIM = mIM;
        this.mComtext = mComtext;
    }

    //获取数据
    @Override
    public void doGetData() {
        BmobQuery<OldThing> query = new BmobQuery<>();
        UserMode userMode = BmobUser.getCurrentUser(UserMode.class);
        query.addWhereEqualTo("author",userMode.getObjectId());
        query.setLimit(10);
        log.d("TAG","id "+userMode.getObjectId());
        query.order("-createdAt");
        query.findObjects(new FindListener<OldThing>() {
            @Override
            public void done(List<OldThing> list, BmobException e) {
                if(e == null){
                    mIM.onResult(true,list);
                }else{
                    mIM.onResult(false,null);
                    log.d("TAG","数据我的资源shibai"+e);
                }
            }
        });
    }
}
