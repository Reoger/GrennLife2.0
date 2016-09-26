package com.reoger.grennlife.encyclopaedia.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/9/18.
 */
public class EncyclopaediaBean extends BmobObject{
    private String mTitle;
    private String mContent;
    private String mSource;
    private String mType;
    private int mNum;

    public int getmNum() {
        return mNum;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
    }

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public EncyclopaediaBean() {
    }

    public EncyclopaediaBean(String mTitle, String mContent, String mSource, String mType) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mSource = mSource;
        this.mType = mType;

    }

    public EncyclopaediaBean(String mTitle, String mContent) {
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}

