package com.reoger.grennlife.monitoring.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 24540 on 2016/9/13.
 */
public class ReportInfo extends BmobObject implements IReportInfo{
    private String title;
    private String content;
    private String num;
    private String ImageUrl;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
//private Date date;


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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
