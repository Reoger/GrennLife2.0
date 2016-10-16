package com.reoger.grennlife.user.monitoringHistroy.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.monitoring.Model.ReportInfo;
import com.reoger.grennlife.user.monitoringHistroy.adapter.MonitoringHistoryAdapter;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 24540 on 2016/10/11.
 */
public class MonitoringHistoryView extends AppCompatActivity{

    private ListView mHistory;
    private TextView mNoInfo;
    private MonitoringHistoryAdapter mAdapter;
    private List<ReportInfo> mData= new ArrayList<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    new toast(MonitoringHistoryView.this,"数据查询成功");
                    break;
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_monitoring_history);
        initView();
        initData();
    }

    private void initData() {
        getDate();
        mAdapter = new MonitoringHistoryAdapter(this,mData);
        if(mData.size()==0){
            mNoInfo.setVisibility(View.VISIBLE);
        }else{
            mNoInfo.setVisibility(View.INVISIBLE);

        }
        mHistory.setAdapter(mAdapter);
    }

    private void initView() {
        mNoInfo = (TextView) findViewById(R.id.user_monitoring_history_no_info);
        mHistory = (ListView) findViewById(R.id.user_monitoring_history_listview);
    }

    private void getDate(){
        ReportInfo info = new ReportInfo();
        BmobQuery<ReportInfo> query = new BmobQuery<>();
        BmobUser user = BmobUser.getCurrentUser(UserMode.class);
        log.d("TAG","用户的iD是"+user.getObjectId());
        query.addWhereEqualTo("userMode",user.getObjectId());
        query.order("-createdAt");
        query.findObjects(new FindListener<ReportInfo>() {
            @Override
            public void done(List<ReportInfo> list, BmobException e) {
                if (e == null){
                    for (ReportInfo info:list
                         ) {
                        log.d("TAG","item"+info.getContent());
                    }
                  mData.addAll(list);
                    log.d("TAG","查询成功");
                    mNoInfo.setVisibility(View.INVISIBLE);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }else{
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                    log.d("TAG","举报历史信息查询失败"+e.toString());
                }
            }
        });
    }
}
