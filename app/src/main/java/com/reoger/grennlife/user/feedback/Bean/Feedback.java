package com.reoger.grennlife.user.feedback.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 24540 on 2016/10/19.
 */
public class Feedback extends BmobObject {
    private String content;
    private String email;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
