package com.reoger.grennlife.law.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zimmerman on 2016/9/27.
 */
public class LawsOpenHelper extends SQLiteOpenHelper {

    //数据库列名
    public static final String LAWS_NUM = "num";
    public static final String LAWS_TITLE = "title";
    public static final String LAWS_CONTENT = "content";

    public static final String TABLE_NAME = "Laws";
    public static final int VERSION = 1;
    //建表语句
    public static final String CREATE_LAWS = "create table "+TABLE_NAME+"(" +
            "num integer primary key autoincrement," +
            "title text," +
            "content text)";
    public LawsOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LAWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
