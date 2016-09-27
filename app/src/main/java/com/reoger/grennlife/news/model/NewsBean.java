package com.reoger.grennlife.news.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zimmerman on 2016/9/26.
 */
public class NewsBean extends BmobObject {
    private String title;
    private String content;
    private String outLine;
    private String date;

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

    public String getOutLine() {
        return outLine;
    }

    public void setOutLine(String outLine) {
        this.outLine = outLine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
