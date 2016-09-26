package com.reoger.grennlife.Recycle.adapter;

import com.reoger.grennlife.Recycle.model.Garbager;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 24540 on 2016/9/20.
 */
public class Datas {

    UserMode user;
    List<Garbager> garbagers = new ArrayList<>();

    /**
     * 获取垃圾回收的数据
     * 1.首先从本地获取
     * 2.后台联网更新
     */
    public  List<Garbager> getData(){
        BmobQuery<Garbager> query = new BmobQuery<Garbager>();
        query.addWhereEndsWith("title","title");
        query.setLimit(10);
        query.findObjects(new FindListener<Garbager>() {
            @Override
            public void done(List<Garbager> list, BmobException e) {
                if(e==null){
                    log.d("bmob","查询成功，共查询到"+list.size()+"条数据");
                    for (Garbager item :
                            list) {
                        log.d("bmob","数据"+item.getContent());
                    }
                    garbagers = list;
                }else{
                    log.d("bmob","数据查询失败");
                }
            }
        });
        return garbagers;
    }


}
