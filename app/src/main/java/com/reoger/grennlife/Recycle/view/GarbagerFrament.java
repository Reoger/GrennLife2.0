package com.reoger.grennlife.Recycle.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;

/**
 * Created by 24540 on 2016/9/26.
 */
public class GarbagerFrament extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.layout_base_main, container, false);
        return messageLayout;
    }
}
