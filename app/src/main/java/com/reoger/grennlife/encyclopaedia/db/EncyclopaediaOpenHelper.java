package com.reoger.grennlife.encyclopaedia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zimmerman on 2016/9/19.
 */
public class EncyclopaediaOpenHelper extends SQLiteOpenHelper {

    //数据库列名
    public static final String BAIKE_NUM = "num";
    public static final String BAIKE_TITLE = "title";
    public static final String BAIKE_CONTENT = "content";
    public static final String BAIKE_RESOURCE = "source";
    public static final String BAIKE_TYPE = "type";

    public static final String TABLE_NAME = "Encyclopaedia";
    public static final int VERSION = 1;
    //建表语句
    public static final String CREATE_ENCYCLOPAEDIA = "create table Encyclopaedia(" +
            "num integer primary key autoincrement," +
            "title text," +
            "content text," +
            "source text," +
            "type text)";


    public EncyclopaediaOpenHelper(Context context, String name,
                                   SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENCYCLOPAEDIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
