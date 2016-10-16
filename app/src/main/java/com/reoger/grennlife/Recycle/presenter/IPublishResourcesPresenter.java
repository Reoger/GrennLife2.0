package com.reoger.grennlife.Recycle.presenter;

import java.util.List;

/**
 * Created by 24540 on 2016/9/19.
 */
public interface IPublishResourcesPresenter {
    void doAddPhoto(int resuletCode);
    void doPublishResouerces(String type,String content,String location,String time,List<String> imageUrl);
}
