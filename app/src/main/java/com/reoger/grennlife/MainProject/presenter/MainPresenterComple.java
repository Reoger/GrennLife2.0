package com.reoger.grennlife.MainProject.presenter;

import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.view.IMainActivity;
import com.reoger.grennlife.Recycle.model.TypeGetData;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 24540 on 2016/9/11.
 */
public class MainPresenterComple implements IMainPresenter {

    private IMainActivity IMainActivity;
    private int dataNum = 10;

    public MainPresenterComple(com.reoger.grennlife.MainProject.view.IMainActivity IMainActivity) {
        this.IMainActivity = IMainActivity;
    }

    //获取数据
    @Override
    public void doGetData(TypeGetData type) {
        initializationData(type);
    }

    /**
     * 获取数据
     */
    void initializationData(final TypeGetData type) {
        switch (type) {
            case INITIALZATION:
                dataNum = 10;
                break;
            case REFRESH:
                dataNum =10;
                break;
            case LOAD_MORE:
                dataNum += 10;
                break;
        }
        BmobQuery<Dynamic> query = new BmobQuery<>();
        query.addWhereNotEqualTo("title", "null");
        query.order("-createdAt");
        query.include("author,likes");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.setLimit(dataNum);
        query.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e == null) {
                    IMainActivity.onResultGetData(true, type, list);
                } else {
                    IMainActivity.onResultGetData(false, type, null);
                }
            }
        });
    }


}
