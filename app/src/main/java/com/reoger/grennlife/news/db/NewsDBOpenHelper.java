package com.reoger.grennlife.news.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zimmerman on 2016/9/26.
 */
public class NewsDBOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "News";
    public static final int VERSION = 1;

    //数据库列名
    public static final String NEWS_NUM = "num";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_CONTENT = "content";
    public static final String NEWS_OUTLINE = "out_line";
    public static final String NEWS_DATE = "date";

    public static final String CREATE_NEWS = "create table News(" +
            "num integer primary key autoincrement," +
            "title text," +
            "content text," +
            "out_line text," +
            "date text)";

    public NewsDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
