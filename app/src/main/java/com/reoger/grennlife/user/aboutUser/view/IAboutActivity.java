package com.reoger.grennlife.user.aboutUser.view;

import com.reoger.grennlife.MainProject.model.Comment;

import java.util.List;

/**
 * Created by 24540 on 2016/10/24.
 */
public interface IAboutActivity {
    void onResultGetData(boolean flag, List<Comment> lists);
}
