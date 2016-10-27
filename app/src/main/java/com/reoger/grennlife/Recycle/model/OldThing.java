package com.reoger.grennlife.Recycle.model;

import com.reoger.grennlife.loginMVP.model.UserMode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 24540 on 2016/9/18.
 */
public class OldThing extends BmobObject{
    private String title;
    private String content;
    private UserMode author;
    private BmobFile image;
    private BmobRelation likes;//多对多的关系，用于存储喜欢该资源的用户
    private String locations;
    private String num;//电话号码
    private String ImageUrl;
    private boolean Available;

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean available) {
        Available = available;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }



    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

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

    public UserMode getAuthor() {
        return author;
    }

    public void setAuthor(UserMode author) {
        this.author = author;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
