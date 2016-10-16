package com.reoger.grennlife.MainProject.presenter;

import com.reoger.grennlife.MainProject.model.Dynamic;

/**
 * Created by 24540 on 2016/10/16.
 */
public interface ICommentPresenter {
    void doPublishAdapter(Dynamic dynamic ,String comment);
    //獲取該條記錄的評論信息
    void doGetComment(Dynamic dynamic);
}
