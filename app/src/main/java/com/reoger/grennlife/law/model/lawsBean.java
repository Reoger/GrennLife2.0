package com.reoger.grennlife.law.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zimmerman on 2016/9/27.
 */
public class LawsBean extends BmobObject {
    private String title;
    private String content;

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
}
