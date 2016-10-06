package com.reoger.grennlife.MainProject.presenter;

import java.util.List;

/**
 * Created by 24540 on 2016/9/26.
 */
public interface IDynamicPresenter {
    void doAddPhoto(int resuletCode);//添加图片
    void doPublish(String titile, String content, List<String> imageUrl);//发布
}
