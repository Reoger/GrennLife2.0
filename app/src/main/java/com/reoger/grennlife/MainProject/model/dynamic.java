package com.reoger.grennlife.MainProject.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 24540 on 2016/9/26.
 */
public class dynamic extends BmobObject{
    private String title;
    private String content;
    private String ImageUrl;

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
