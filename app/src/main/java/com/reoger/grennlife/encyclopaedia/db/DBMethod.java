package com.reoger.grennlife.encyclopaedia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;

import java.util.ArrayList;

/**
 * Created by Zimmerman on 2016/9/19.
 */
public class DBMethod {

    private static DBMethod dbMethod;
    public static final int VERSION = 1;
    public static final String DB_NAME = "Encyclopaedia";
    private SQLiteDatabase db;

    //私有化构造方法
    private DBMethod(Context context) {
        EncyclopaediaOpenHelper helper = new EncyclopaediaOpenHelper(context,DB_NAME,
                null,VERSION);
        db = helper.getWritableDatabase();
    }
    public Cursor queryTitle(String titleName) {
        return db.query(DB_NAME, new String[]{EncyclopaediaOpenHelper.BAIKE_TITLE},"where column = ?", new String[]{titleName},null,null,null);
    }

    public synchronized static DBMethod getInstance(Context context) {
        if (dbMethod == null) {
            dbMethod = new DBMethod(context);
        }
        return dbMethod;
    }
    /**
     * 将一个JAVAbean数据存入数据库
     * @param one
     */
    public void saveEncyclopaedia(EncyclopaediaBean one) {
        if (one != null) {
            ContentValues values = new ContentValues();
            values.put(EncyclopaediaOpenHelper.BAIKE_TITLE,one.getmTitle());
            values.put(EncyclopaediaOpenHelper.BAIKE_CONTENT,one.getmContent());
            values.put(EncyclopaediaOpenHelper.BAIKE_RESOURCE,one.getmSource());
            values.put(EncyclopaediaOpenHelper.BAIKE_TYPE,one.getmType());
            db.insert(DB_NAME,null,values);
        }
    }
    //从本地数据库读取五条的百科词条信息
    public ArrayList<EncyclopaediaBean> loadEncyclopaedia() {
        ArrayList<EncyclopaediaBean> datas = new ArrayList<>();
        Cursor cursor = db.query(DB_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
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


            }while (cursor.moveToNext() && datas.size()< 5);
        }

        if (cursor != null) {
            cursor.close();
        }

        return datas;
    }
}
