package com.reoger.grennlife.MainProject.view;

import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.Recycle.model.TypeGetData;

import java.util.List;

/**
 * Created by 24540 on 2016/9/10.
 */
public interface IMainActivity {
    void onResultGetData(boolean flag, TypeGetData data, List<Dynamic> lists);
}
