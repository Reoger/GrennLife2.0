package com.reoger.grennlife.MainProject.model;

/**
 * Created by 24540 on 2016/9/10.
 */
public class CarouselInfo {

    private String url;
    private String title;

    public CarouselInfo(String title, String url) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

