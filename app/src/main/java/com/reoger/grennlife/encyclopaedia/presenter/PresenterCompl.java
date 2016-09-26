package com.reoger.grennlife.encyclopaedia.presenter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.reoger.grennlife.encyclopaedia.db.DBMethod;
import com.reoger.grennlife.encyclopaedia.model.EncyclopaediaBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zimmerman on 2016/9/19.
 */
public class PresenterCompl implements com.reoger.grennlife.encyclopaedia.presenter.IPresenter {

    private String TAG = "tag";

    /**
     * 这个方法内测调用，用来创建对应对象的数据表，只要创建了数据表以后就不用调用了，若是数据表误删，
     * 则调用此方法创建数据表
     */
    @Override
    public void doInitDBForJavaBean() {

        EncyclopaediaBean mBeanItem = new EncyclopaediaBean("init","init","init","init");
        mBeanItem.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Log.d(TAG,"添加数据成功，返回objectId为："+objectId);
                }else{
//                    toast("创建数据失败：" + e.getMessage());
                    Log.d(TAG,"创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    //从网上数据库读取最新所有的词条
    @Override
    public ArrayList<EncyclopaediaBean> getEncyclopaediaData() {
        final ArrayList<EncyclopaediaBean> datas = new ArrayList<>();
        BmobQuery<EncyclopaediaBean> query = new BmobQuery<EncyclopaediaBean>();
        query.addWhereNotEqualTo("mType", "Barbie");
        query.findObjects(new FindListener<EncyclopaediaBean>() {
            @Override
            public void done(List<EncyclopaediaBean> list, BmobException e) {
                for (EncyclopaediaBean bean : list) {
                    datas.add(bean);
                }
            }
        });
        Log.d("TAG","log before get data return");
        return datas;
    }

    @Override
    public ArrayList<EncyclopaediaBean> getDataFromDB(Context context) {
        ArrayList<EncyclopaediaBean> datas;
        DBMethod method = DBMethod.getInstance(context);
        datas = method.loadEncyclopaedia();

        return datas;
    }

    /**
     * 不重复的存入数据库
     * @param datas
     * @param context
     */
    @Override
    public void doSaveDataIntoDB(ArrayList<EncyclopaediaBean> datas,Context context) {
        DBMethod db = DBMethod.getInstance(context);
        for (EncyclopaediaBean one : datas) {
            //如果数据库已经重复了，那么就不存入数据
            Cursor cursor = db.queryTitle(one.getmTitle());
            if (cursor == null) {
                db.saveEncyclopaedia(one);
            }
        }
    }

    @Override
    public void doReadMoreDataFromNetServe(Context context) {
            //如果本地数据库继续读取不足五条，那么再从网上读取更多进入数据库
            doSaveDataIntoDB(getEncyclopaediaData(),context);

    }
}
