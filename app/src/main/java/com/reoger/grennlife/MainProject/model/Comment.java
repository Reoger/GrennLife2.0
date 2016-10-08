package com.reoger.grennlife.MainProject.model;

import com.reoger.grennlife.loginMVP.model.UserMode;

import cn.bmob.v3.BmobObject;

/**
 * Created by 24540 on 2016/10/5.
 */
public class Comment extends BmobObject{
    private String content;//评论的内容
    private UserMode user;//评论用户
    private Dynamic dynamic;//所评论的动态

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserMode getUser() {
        return user;
    }

    public void setUser(UserMode user) {
        this.user = user;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }
}
