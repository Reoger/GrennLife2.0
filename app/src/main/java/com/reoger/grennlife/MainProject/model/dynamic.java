package com.reoger.grennlife.MainProject.model;

import com.reoger.grennlife.loginMVP.model.UserMode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 24540 on 2016/9/26.
 */
public class Dynamic extends BmobObject{
    private String title;
    private String content;
    private String ImageUrl;
    private UserMode author;//发布者

    public UserMode getAuthor() {
        return author;
    }

    public void setAuthor(UserMode author) {
        this.author = author;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    private BmobRelation likes;//用于储存喜欢该用户的所有用户


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }


}
