package com.reoger.grennlife.MainProject.view;


import com.reoger.grennlife.MainProject.model.Comment;

import java.util.List;

/**
 * Created by 24540 on 2016/10/16.
 */
public interface ICommentView {
    void onResultPublishComment(boolean flag,String code);
    //獲得記錄的評論
     void onResultGetComment(boolean flag,List<Comment> list);
}
