package com.reoger.grennlife.utils.ServerDataOperation;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;
import com.reoger.grennlife.encyclopaedia.view.EncyclopaediaView;
import com.reoger.grennlife.law.db.LawsOpenHelper;
import com.reoger.grennlife.law.model.LawsBean;
import com.reoger.grennlife.law.view.LawView;
import com.reoger.grennlife.news.db.NewsDBOpenHelper;
import com.reoger.grennlife.news.model.NewsBean;
import com.reoger.grennlife.news.view.NewsView;
import com.reoger.grennlife.technology.db.TechnologyOpenHelper;
import com.reoger.grennlife.technology.model.TechnologyBean;
import com.reoger.grennlife.technology.view.TechnologyView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Zimmerman on 2016/9/24.
 */
public class ServerDataCompl implements IServerData {
    public static final int BEAN_TYPE_ENCYCLOPAEDIA = 1;
    public static final int BEAN_TYPE_NEWS = 2;
    public static final int BEAN_TYPE_LAWS = 3;
    public static final int BEAN_TYPE_TECHNOLOGY = 4;

    private ArrayList<BmobObject> datas = new ArrayList<>();

    //实例用来后台线程得到数据后通知该view去刷新数据 哈哈
    private LawView mLawView;
    private EncyclopaediaView mEncyclopaediaView;
    private NewsView mNewsView;
    private TechnologyView mTechnologyView;

    @Override
    public ArrayList<BmobObject> getDataFromServer(int beanType) {
        switch (beanType) {
            case BEAN_TYPE_ENCYCLOPAEDIA:
                return encyclopaediaType();
            case BEAN_TYPE_NEWS:
                return newsType();
            case BEAN_TYPE_LAWS:
                return lawsType();
            case BEAN_TYPE_TECHNOLOGY:
                return technologyType();

        }
        return null;
    }

    @Override
    public ArrayList<BmobObject> getDataFromServer(int beanType, Activity myView) {
        switch (beanType) {
            case BEAN_TYPE_ENCYCLOPAEDIA:
                this.mEncyclopaediaView = (EncyclopaediaView) myView;
                return encyclopaediaType();
            case BEAN_TYPE_NEWS:
                this.mNewsView= (NewsView) myView;
                return newsType();
            case BEAN_TYPE_LAWS:
                this.mLawView = (LawView) myView;
                return lawsType();
            case BEAN_TYPE_TECHNOLOGY:
                this.mTechnologyView = (TechnologyView) myView;
                return technologyType();
        }
        return null;
    }

    //从网络后台获取环保科技相关的词条
    private ArrayList<BmobObject> technologyType() {
//        datas = new ArrayList<>();
        BmobQuery<TechnologyBean> query = new BmobQuery<TechnologyBean>();
        query.addWhereNotEqualTo(TechnologyOpenHelper.TECHNOLOGY_TITLE, "Barbie");
        query.findObjects(new FindListener<TechnologyBean>() {
            @Override
            public void done(List<TechnologyBean> list, BmobException e) {
                if (e == null) {
                    for (TechnologyBean bean : list) {
                        datas.add(bean);
                    }
                    Log.d("qqw", " done :" + datas.size());
                    if (mTechnologyView != null) {
                        mTechnologyView.mHandler.sendEmptyMessage(10);
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        Log.d("qqe", "log before get data return" + datas.size());
        return datas;
    }

    //从网络后台获取法律法规相关词条
    private ArrayList<BmobObject> lawsType() {
//        datas = new ArrayList<>();
        BmobQuery<LawsBean> query = new BmobQuery<LawsBean>();
        query.addWhereNotEqualTo(LawsOpenHelper.LAWS_TITLE, "Barbie");
        query.findObjects(new FindListener<LawsBean>() {
            @Override
            public void done(List<LawsBean> list, BmobException e) {
                if (e == null) {
                    for (LawsBean bean : list) {
                        datas.add(bean);
                    }
                    if (mLawView != null) {
                        mLawView.mHandler.sendEmptyMessage(10);
                    }
                    Log.d("qqw", " done :" + datas.size());
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        Log.d("qqe", "log before get data return" + datas.size());
        return datas;
    }

    //从网络后台获取新闻词条
    private ArrayList<BmobObject> newsType() {
//        datas = new ArrayList<>();
        BmobQuery<NewsBean> query = new BmobQuery<NewsBean>();
//        query.addWhereNotEqualTo(NewsDBOpenHelper.NEWS_OUTLINE, "Barbie");
        query.addWhereNotEqualTo("outLine", "Barbie");
        query.findObjects(new FindListener<NewsBean>() {
            @Override
            public void done(List<NewsBean> list, BmobException e) {
                if (e == null) {
                    for (NewsBean bean : list) {
                        datas.add(bean);
                    }
                    Log.d("qqw", " done :" + datas.size());
                    if (mNewsView != null) {
                        mNewsView.mHandler.sendEmptyMessage(10);
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        Log.d("qqe", "log before get data return" + datas.size());
        return datas;
    }

    private ArrayList<BmobObject> encyclopaediaType() {
        //从网上数据库读取最新所有的词条
//        datas = new ArrayList<>();
        BmobQuery<EncyclopaediaBean> query = new BmobQuery<EncyclopaediaBean>();
        query.addWhereNotEqualTo("mType", "Barbie");
        query.findObjects(new FindListener<EncyclopaediaBean>() {
            @Override
            public void done(List<EncyclopaediaBean> list, BmobException e) {
                if (e == null) {
                    for (EncyclopaediaBean bean : list) {
                        datas.add(bean);
                    }
                    Log.d("qqw", " done :" + datas.size());
                    if (mEncyclopaediaView != null) {
                        mEncyclopaediaView.mHandler.sendEmptyMessage(10);
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        Log.d("qqe", "log before get data return" + datas.size());
        return datas;
    }
}
