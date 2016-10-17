package com.reoger.grennlife.Recycle.view;

import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.Recycle.model.TypeGetData;

import java.util.List;

/**
 * Created by 24540 on 2016/10/16.
 */
public interface IOldthing {
    void onGetResultData(boolean flag, TypeGetData type, List<OldThing> lists);
}
