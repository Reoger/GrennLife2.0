package com.reoger.grennlife.monitoring.Model;

import com.reoger.grennlife.loginMVP.model.UserMode;

import cn.bmob.v3.BmobObject;

/**
 * Created by 24540 on 2016/9/13.
 */
public class ReportInfo extends BmobObject implements IReportInfo{
    private String title;
    private String content;
    private String num;
    private String ImageUrl;
    private UserMode userMode;
    /**
     * 用于标识举报后的进度
     * 1.表示审核中
     * 2.表示未通过审核
     * 3.表示通过审核
     */
    private Integer statuts;


    public UserMode getUserMode() {
        return userMode;
    }

    public void setUserMode(UserMode userMode) {
        this.userMode = userMode;
    }
    public Integer getStatuts() {
        return statuts;
    }

    public void setStatuts(Integer statuts) {
        this.statuts = statuts;
    }

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
