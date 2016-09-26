package com.reoger.grennlife.utils;

import okhttp3.OkHttpClient;

/**
 * Created by 24540 on 2016/9/12.
 */
public class OkHttpUtils {

    private OkHttpClient mOkHttpClient;

    public OkHttpUtils(OkHttpClient mOkHttpClient) {
        if (mOkHttpClient == null) {
            this.mOkHttpClient = new OkHttpClient();
        } else {
            this.mOkHttpClient = mOkHttpClient;
        }
    }


}
