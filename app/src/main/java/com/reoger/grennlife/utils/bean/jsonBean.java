package com.reoger.grennlife.utils.bean;

/**
 * Created by 24540 on 2016/10/27.
 */
public class jsonBean {

    /**
     * title : reoger
     * context : this is the content
     * objectId : 123456
     */

    private String title;
    private String context;
    private String objectId;
    private String url;

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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    /**
     *
     {
     "title": "reoger",
     "context": "this is the content",
     "objectId": "e22bf16c6c"
     "url":"http://reoger.cc"
     }
     */
}
