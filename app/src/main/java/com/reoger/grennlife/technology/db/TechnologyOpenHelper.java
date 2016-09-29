package com.reoger.grennlife.technology.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zimmerman on 2016/9/19.
 */
public class TechnologyOpenHelper extends SQLiteOpenHelper {

    //数据库列名
    public static final String TECHNOLOGY_NUM = "num";
    public static final String TECHNOLOGY_TITLE = "title";
    public static final String TECHNOLOGY_CONTENT = "content";

    public static final String TABLE_NAME = "Technology";
    public static final int VERSION = 1;
    //建表语句
    public static final String CREATE_TECHNOLOGY = "create table Technology(" +
            "num integer primary key autoincrement," +
            "title text," +
            "content text)";


    public TechnologyOpenHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TECHNOLOGY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
