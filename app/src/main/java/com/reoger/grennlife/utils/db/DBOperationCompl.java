package com.reoger.grennlife.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.reoger.grennlife.encyclopaedia.db.EncyclopaediaOpenHelper;
import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;
import com.reoger.grennlife.law.db.LawsOpenHelper;
import com.reoger.grennlife.law.model.LawsBean;
import com.reoger.grennlife.news.db.NewsDBOpenHelper;
import com.reoger.grennlife.news.model.NewsBean;
import com.reoger.grennlife.technology.db.TechnologyOpenHelper;
import com.reoger.grennlife.technology.model.TechnologyBean;
import com.reoger.grennlife.utils.ServerDataOperation.ServerDataCompl;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/9/25.
 */
public class DBOperationCompl implements IDBOperation {

    /**
     * 这个类是公用的，就是大家都可以用这个类来存储数据到数据库，
     * 而区分的方式就是通过表名来区分了
     * 用来存储百科类的数据库操作对象，避免重复打开数据库
     */

    private static DBOperationCompl mPaediaCompl;
    private static DBOperationCompl mNewsCompl;
    private static DBOperationCompl mLawsCompl;
    private static DBOperationCompl mTechnologyCompl;

    private SQLiteDatabase db = null;
    private int mBeanType;
    private String mTableName;

    public int getmBeanType() {
        return mBeanType;
    }

    public String getmTableName() {
        return mTableName;
    }

    private DBOperationCompl(Context context, int beanType) {
        this.mBeanType = beanType;
        Log.d("qqe4","beanType: " + beanType);
        //包含了百科和新闻
        switch (beanType) {
            case ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA:
                Log.d("qqe 1234","aftern into bean laws");

                this.mTableName = EncyclopaediaOpenHelper.TABLE_NAME;
                EncyclopaediaOpenHelper helper = new EncyclopaediaOpenHelper(context, EncyclopaediaOpenHelper.TABLE_NAME,
                        null, EncyclopaediaOpenHelper.VERSION);

                db = helper.getWritableDatabase();
                break;
            case ServerDataCompl.BEAN_TYPE_NEWS:
                Log.d("qqe 123","aftern into bean laws");

                this.mTableName = NewsDBOpenHelper.TABLE_NAME;
                NewsDBOpenHelper newsHelper = new NewsDBOpenHelper(context, NewsDBOpenHelper.TABLE_NAME,
                        null, NewsDBOpenHelper.VERSION);
                db = newsHelper.getWritableDatabase();
                break;
            case ServerDataCompl.BEAN_TYPE_LAWS:
                Log.d("qqe 12","aftern into bean laws");
                this.mTableName = LawsOpenHelper.TABLE_NAME;
                LawsOpenHelper lawsOpenHelper = new LawsOpenHelper(context, LawsOpenHelper.TABLE_NAME,
                        null, LawsOpenHelper.VERSION);
                db = lawsOpenHelper.getWritableDatabase();
                break;
            case ServerDataCompl.BEAN_TYPE_TECHNOLOGY:
                this.mTableName = TechnologyOpenHelper.TABLE_NAME;
                TechnologyOpenHelper technologyOpenHelper = new TechnologyOpenHelper(
                        context, TechnologyOpenHelper.TABLE_NAME,
                        null, TechnologyOpenHelper.VERSION);
                db = technologyOpenHelper.getWritableDatabase();
                break;
            default:
                Log.d("qqe w","default:");
                break;
        }
    }

    //所有类似的通用此方法
    public synchronized static DBOperationCompl getInstance(Context context, int beanType) {

        switch (beanType) {
            case ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA:
                if (mPaediaCompl == null) {
                    mPaediaCompl = new DBOperationCompl(context, beanType);
                }
                Log.d("qqe for mPaediaCompl", "getInstance: ");
                return mPaediaCompl;
            case ServerDataCompl.BEAN_TYPE_NEWS:
                if (mNewsCompl == null) {
                    mNewsCompl = new DBOperationCompl(context,beanType);
                }
                Log.d("qqe ", "getInstance: for return mNewsCompl");
                return mNewsCompl;
            case ServerDataCompl.BEAN_TYPE_LAWS:
                if (mLawsCompl == null) {
                    mLawsCompl = new DBOperationCompl(context,beanType);
                    Log.d("qqe 8", "getInstance: for return mLawsCompl");
                }
                return mLawsCompl;
            case ServerDataCompl.BEAN_TYPE_TECHNOLOGY:
//                mTechnologyCompl
                if (mTechnologyCompl == null) {
                    mTechnologyCompl = new DBOperationCompl(context,beanType);
                    Log.d("qqe 8", "getInstance: for return techCompl");
                }
                return mTechnologyCompl;

        }
        return null;
    }

    /**
     * 不重复的存入数据，该方法对外开放
     *
     * @param datas
     */
    @Override
    public void doSaveDataIntoDB(ArrayList<BmobObject> datas) {
//        Log.d("qqe5","in do savce data "+mTableName + mBeanType + datas.size() + datas.get(0).getTableName());
        for (BmobObject one : datas) {
            //如果数据库已经重复了，那么就不存入数据
            //处理包含了百科，新闻
            switch (mBeanType) {
                case ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA:
                    Cursor cursor = queryTitle(
                            ((EncyclopaediaBean) one).getmTitle(),
                            EncyclopaediaOpenHelper.BAIKE_TITLE);
                    //cursor为空，则数据库并不存在，可以存入
                    if (cursor.getCount() == 0) {
                        saveAData(one);
                    }
                    break;
                case ServerDataCompl.BEAN_TYPE_NEWS:
                    Cursor newsCursor = queryTitle(
                            ((NewsBean) one).getTitle(),
                            NewsDBOpenHelper.NEWS_TITLE);
                    //cursor为空，则数据库并不存在，可以存入
                    Log.d("qqe ","in do save data beantype:" + mBeanType);
                    Log.d("qqe", "in doSave data tablename" + mTableName);
                    if (newsCursor.getCount() == 0) {
                        saveAData(one);
                    }
                    break;
                case ServerDataCompl.BEAN_TYPE_LAWS:
                    Cursor lawsCursor = queryTitle(
                            ((LawsBean) one).getTitle(),
                            LawsOpenHelper.LAWS_TITLE);
                    //cursor为空，则数据库并不存在，可以存入
                    Log.d("qqe ","in do save data beantype:" + mBeanType);
                    Log.d("qqe", "in doSave data tablename" + mTableName);
                    if (lawsCursor.getCount() == 0) {
                        saveAData(one);
                    }
                    break;
                case ServerDataCompl.BEAN_TYPE_TECHNOLOGY:
                    Cursor technologyCursor = queryTitle(
                            ((TechnologyBean) one).getTitle(),
                            TechnologyOpenHelper.TECHNOLOGY_TITLE);
                    //cursor为空，则数据库并不存在，可以存入
                    Log.d("qqe ","in do save data beantype:" + mBeanType);
                    Log.d("qqe", "in doSave data tablename" + mTableName);
                    if (technologyCursor.getCount() == 0) {
                        saveAData(one);
                    }
                    break;
            }

        }
    }


    //得到对应列的查询结果，这样避免重复写入数据
    private Cursor queryTitle(String titleName, String titleKey) {
        return db.query(mTableName, new String[]{titleKey}, titleKey + " = ?",
                new String[]{titleName}, null, null, null);
    }

    /**
     * 从本地数据库读取百科词条信息
     */
    @Override
    public ArrayList<BmobObject> getDataFromLocalDB() {
        ArrayList<BmobObject> datas = new ArrayList<>();
        Cursor cursor = db.query(mTableName, null, null, null, null, null, null);
        Log.d("qqe", "data size in local db  -1 :" + datas.size());
        Log.d("qqe", "db:" + cursor.getCount());

        if (cursor.moveToFirst()) {
            Log.d("qqe", "data size in local db :" + datas.size());
            //这里包含了百科和新闻
            switch (mBeanType) {
                case ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA:
                    //获取百科信息的逻辑写成一个方法
                    for (BmobObject one : doGetEncyclopaediaArray(cursor)) {
                        datas.add(one);
                    }
                    Log.d("qqe1", "data size in local db :" + datas.size());
                    break;

                case ServerDataCompl.BEAN_TYPE_NEWS:
                    //获取新闻的一个方法：
                    Log.d("qqe3","qweqwe");
                    for (BmobObject one : doGetNewsArray(cursor)) {
                        datas.add(one);
                    }
                    Log.d("qqe2", "news data size in local db :" + datas.size());
                    break;
                case ServerDataCompl.BEAN_TYPE_LAWS:
                    //获取法律的一个方法：
                    for (BmobObject one : doGetLawsArray(cursor)) {
                        datas.add(one);
                    }
                    Log.d("qqe2", "laws data size in local db :" + datas.size());
                    break;
                case ServerDataCompl.BEAN_TYPE_TECHNOLOGY:
                    for (BmobObject one : doGetTechnologyArray(cursor)) {
                        datas.add(one);
                    }
                    Log.d("qqe2", "laws data size in local db :" + datas.size());
                    break;
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return datas;
    }

    //获取环保科技所有
    private ArrayList<BmobObject> doGetTechnologyArray(Cursor cursor) {
        ArrayList<BmobObject> datas = new ArrayList<>();
        do {
            TechnologyBean one = new TechnologyBean();
            //标题
            one.setTitle(cursor.getString(
                    cursor.getColumnIndex(TechnologyOpenHelper.TECHNOLOGY_TITLE)));
            //内容
            one.setContent(cursor.getString(
                    cursor.getColumnIndex(TechnologyOpenHelper.TECHNOLOGY_CONTENT)));

            datas.add(one);


        } while (cursor.moveToNext());

        return datas;
    }
    //获取法律私有
    private ArrayList<BmobObject> doGetLawsArray(Cursor cursor) {
        ArrayList<BmobObject> datas = new ArrayList<>();
        do {
            LawsBean one = new LawsBean();
            //标题
            one.setTitle(cursor.getString(
                    cursor.getColumnIndex(LawsOpenHelper.LAWS_TITLE)));
            //内容
            one.setContent(cursor.getString(
                    cursor.getColumnIndex(LawsOpenHelper.LAWS_CONTENT)));

            datas.add(one);


        } while (cursor.moveToNext());
        return datas;
    }
    //为获取新闻的私有
    private ArrayList<BmobObject> doGetNewsArray(Cursor cursor) {
        ArrayList<BmobObject> datas = new ArrayList<>();
        do {
            NewsBean one = new NewsBean();
            //标题
            one.setTitle(cursor.getString(
                    cursor.getColumnIndex(NewsDBOpenHelper.NEWS_TITLE)));
            //内容
            one.setContent(cursor.getString(
                    cursor.getColumnIndex(NewsDBOpenHelper.NEWS_CONTENT)));
            //新闻概要
            one.setOutLine(cursor.getString(
                    cursor.getColumnIndex(NewsDBOpenHelper.NEWS_OUTLINE)));
//            新闻日期
            one.setDate(cursor.getString(
                    cursor.getColumnIndex(NewsDBOpenHelper.NEWS_DATE)
            ));
            datas.add(one);


        } while (cursor.moveToNext());

        return datas;
    }

    //私有方法，从查询结果中得到百科的数组
    private ArrayList<BmobObject> doGetEncyclopaediaArray(Cursor cursor) {
        ArrayList<BmobObject> datas = new ArrayList<>();
        do {
            EncyclopaediaBean one = new EncyclopaediaBean();
            one.setmTitle(cursor.getString(
                    cursor.getColumnIndex(EncyclopaediaOpenHelper.BAIKE_TITLE)));
            one.setmContent(cursor.getString(
                    cursor.getColumnIndex(EncyclopaediaOpenHelper.BAIKE_CONTENT)));
            one.setmSource(cursor.getString(
                    cursor.getColumnIndex(EncyclopaediaOpenHelper.BAIKE_RESOURCE)));
            one.setmType(cursor.getString(
                    cursor.getColumnIndex(EncyclopaediaOpenHelper.BAIKE_TYPE)
            ));
            one.setmNum(cursor.getInt(
                    cursor.getColumnIndex(EncyclopaediaOpenHelper.BAIKE_NUM)
            ));

            datas.add(one);


        } while (cursor.moveToNext());

        return datas;
    }


    /**
     * 将一个JAVAbean数据存入数据库,私有方法不对外开放
     *
     * @param one
     */
    private void saveAData(BmobObject one) {
        if (one != null) {
            ContentValues values = new ContentValues();
            switch (mBeanType) {
                case ServerDataCompl.BEAN_TYPE_ENCYCLOPAEDIA:
                    values.put(EncyclopaediaOpenHelper.BAIKE_TITLE, ((EncyclopaediaBean) one).getmTitle());
                    values.put(EncyclopaediaOpenHelper.BAIKE_CONTENT, ((EncyclopaediaBean) one).getmContent());
                    values.put(EncyclopaediaOpenHelper.BAIKE_RESOURCE, ((EncyclopaediaBean) one).getmSource());
                    values.put(EncyclopaediaOpenHelper.BAIKE_TYPE, ((EncyclopaediaBean) one).getmType());
                    long i = db.insert(EncyclopaediaOpenHelper.TABLE_NAME, null, values);
                    break;
                case ServerDataCompl.BEAN_TYPE_LAWS:
                    values.put(LawsOpenHelper.LAWS_TITLE, ((LawsBean) one).getTitle());
                    values.put(LawsOpenHelper.LAWS_CONTENT, ((LawsBean) one).getContent());
                    db.insert(LawsOpenHelper.TABLE_NAME, null, values);
                    break;
                case ServerDataCompl.BEAN_TYPE_NEWS:
                    values.put(NewsDBOpenHelper.NEWS_TITLE, ((NewsBean) one).getTitle());
                    values.put(NewsDBOpenHelper.NEWS_CONTENT, ((NewsBean) one).getContent());
                    values.put(NewsDBOpenHelper.NEWS_OUTLINE, ((NewsBean) one).getOutLine());
                    values.put(NewsDBOpenHelper.NEWS_DATE, ((NewsBean) one).getDate());
                    db.insert(NewsDBOpenHelper.TABLE_NAME, null, values);
                    break;
                case ServerDataCompl.BEAN_TYPE_TECHNOLOGY:
                    values.put(TechnologyOpenHelper.TECHNOLOGY_TITLE, ((
                            TechnologyBean) one).getTitle());
                    values.put(TechnologyOpenHelper.TECHNOLOGY_CONTENT, ((
                            TechnologyBean) one).getContent());
                    db.insert(TechnologyOpenHelper.TABLE_NAME, null, values);
                    break;
            }
        }
    }
}