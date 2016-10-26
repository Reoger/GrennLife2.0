package com.reoger.grennlife.upDate.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.reoger.grennlife.utils.CustomApplication;

/**
 * Created by admin on 2016/10/26.
 */
public class PresenterCompl implements IUpdatePresenter {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mPasswordEditor;

    public static final String NET_UPDATE_IS_CHECK = "net_update";
    public static final String NET_UPDATE_AUTO_IS_CHECK = "net_auto_update";

    @Override
    public void rememberNetWorkState(boolean isChecked) {
        mPref = PreferenceManager.getDefaultSharedPreferences(CustomApplication.getContext());
        mPasswordEditor = mPref.edit();
        mPasswordEditor.putBoolean(NET_UPDATE_IS_CHECK, isChecked);
        mPasswordEditor.commit();
    }

    @Override
    public void rememberAutoUpdate(boolean isChecked) {
        mPref = PreferenceManager.getDefaultSharedPreferences(CustomApplication.getContext());
        mPasswordEditor = mPref.edit();
        mPasswordEditor.putBoolean(NET_UPDATE_AUTO_IS_CHECK, isChecked);
        mPasswordEditor.commit();
    }

}

