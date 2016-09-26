package com.reoger.grennlife.utils;

import android.util.Log;

/**
 * Created by 24540 on 2016/9/13.
 */
public class log {
    private static boolean mIsPublic = false;

    public static void d(String TAG,String str){
        if(!mIsPublic){
            Log.d(TAG,str);
        }
    }

    public static void i(String TAG,String str){
        if(!mIsPublic){
            Log.i(TAG,str);
        }
    }

    public static void e(String TAG,String str){
        if(!mIsPublic){
            Log.e(TAG,str);
        }
    }

    public static void v(String TAG,String str){
        if(!mIsPublic){
            Log.v(TAG,str);
        }
    }
}
