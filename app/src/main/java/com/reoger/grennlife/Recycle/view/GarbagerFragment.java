package com.reoger.grennlife.Recycle.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.adapter.GarbagerAdapter;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/26.
 */
public class GarbagerFragment extends Fragment{

    private ArrayList<UserMode> mDatas = new ArrayList<UserMode>();
    private RefreshRecyclerView recyclerView;
    private GarbagerAdapter mAdapter;
    private View rootView;


    private static final int REFRESH = 0x1001;
    private static final int INITIALZATION = 0x1002;
    private static final int LOAD_MORE = 0x1003;
    private static final int INITIALZATION_FAIL = 0x1004;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.layout_base_main_recycle2, container, false);
        rootView=messageLayout;
        initView();
        return messageLayout;
    }


    private void initView() {
        Context context = getContext();
        View header = View.inflate(context,R.layout.recycle_header2,null);
        recyclerView = (RefreshRecyclerView) rootView.findViewById(R.id.dynamic_recyclerView2);
        initData();
        mAdapter = new GarbagerAdapter(context,mDatas);
        RecyclerViewManager.with(mAdapter,new LinearLayoutManager(context))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        refreshData();//刷新數據
                    }

                    @Override
                    public void onLoadMore() {
                        loadMoreData();
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                android.widget.Toast.makeText(getContext(), "item" + position, android.widget.Toast.LENGTH_SHORT).show();
            }
        }).into(recyclerView,context);
    }



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case INITIALZATION:
                    new toast(getContext(),"添加數據成功");
                    break;
                case REFRESH:
                    new toast(getContext(),"刷新成功");
                    break;
                case LOAD_MORE:
                    new toast(getContext(),"加载更多");
                    break;
                case  INITIALZATION_FAIL:
                    new toast(getContext(),"初始化失败");
                    break;
            }
            recyclerView.onRefreshCompleted();
            mAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 初始化數據
     */
    void initData(){
        BmobQuery<UserMode> query = new BmobQuery<>();
        query.addWhereEqualTo("State",2);

        query.setLimit(10);
        query.order("-createdAt");
        query.findObjects(new FindListener<UserMode>() {
            @Override
            public void done(List<UserMode> list, BmobException e) {
                if(e==null){
                    log.d("TAG","查詢成功");
//                    mDatas.removeAll(list);
                    mDatas.addAll(list);
                    Message message = new Message();
                    message.what=INITIALZATION;
                    mHandler.sendMessage(message);
                }else{
                    Message message = new Message();
                    message.what=INITIALZATION_FAIL;
                    mHandler.sendMessage(message);//刷新失败也需要做处理
                    log.d("TAG","查詢失敗");
                }
            }
        });
    }
    /**
     * 刷新數據
     */
    void  refreshData(){
            if (mDatas.size() >0){
                BmobQuery<UserMode> query = new BmobQuery<>();
                String start = mDatas.get(0).getCreatedAt();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(start);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                query.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
                BmobQuery<UserMode> query1 = new BmobQuery<>();
                query.addWhereEqualTo("State",2);

                List<BmobQuery<UserMode>> queries = new ArrayList<>();
                queries.add(query);
                queries.add(query1);

                BmobQuery<UserMode> bmobQuery = new BmobQuery<>();
                bmobQuery.and(queries);

                bmobQuery.findObjects(new FindListener<UserMode>() {
                    @Override
                    public void done(List<UserMode> list, BmobException e) {
                        if(e == null){
                            list.remove(0);
                            if(list.size()>0){
                                mDatas.addAll(0,list);
                            }else{
                                log.d("TAG","刷新并沒有獲得數據");
                            }
                            Message msg = new Message();
                            msg.what = REFRESH;
                            mHandler.sendMessage(msg);
                        }else{
                            log.d("YYY","ggg"+e.toString());
                        }
                    }
                });
            }else{
                initData();
            }
    }

    /**
     * 下拉加载更多
     */
    private void loadMoreData() {
        BmobQuery<UserMode> query = new BmobQuery<UserMode>();
        int position = mDatas.size() - 1;
        if(position <= 0 ){
            new toast(getContext(),"加载不可用");
            initData();
        }else{
            String start = mDatas.get(position).getCreatedAt();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));

            BmobQuery<UserMode> query1 = new BmobQuery<>();
            query1.addWhereEqualTo("State",2);

            List<BmobQuery<UserMode>> queries = new ArrayList<>();
            queries.add(query);
            queries.add(query1);


            BmobQuery<UserMode> bmobQuery = new BmobQuery<>();
            bmobQuery.and(queries);

            bmobQuery.setLimit(5);
            bmobQuery.order("-createdAt");
            bmobQuery.findObjects(new FindListener<UserMode>() {
                @Override
                public void done(List<UserMode> list, BmobException e) {
                    if(e == null){
                        list.remove(0);
                        if(list.size()>0){
                            mDatas.addAll(list);
                        }else {
                            log.d("TAG","没有数据");
                        }
                        Message msg = new Message();
                        msg.what = LOAD_MORE;
                        mHandler.sendMessage(msg);
                    }else{
                        log.d("TAG", e.toString() + "错误码");
                        Message msg = new Message();
                        msg.what = LOAD_MORE;
                        mHandler.sendMessage(msg);
                    }
                }
            });
        }
    }

}