package com.reoger.grennlife.user.myResuouers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.user.myResuouers.adapter.ResourcesAdapter;
import com.reoger.grennlife.user.myResuouers.prestent.IResourcesPresntens;
import com.reoger.grennlife.user.myResuouers.prestent.ResourcesPresents;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/10/17.
 */
public class MyResources extends AppCompatActivity implements IMyResources,AdapterView.OnItemClickListener,View.OnClickListener{
    private IResourcesPresntens mIRs;
    private ResourcesAdapter mAdapter;
    private List<OldThing> mData= new ArrayList<>();
    private ListView mListView;
    private ImageButton mBack;
    private TextView mNone;

    public static final String OLDTHING = "oldThing";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_resources);
        initView();
        initData();

    }

    private void initData() {
        mIRs.doGetData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.myresourcer_listview);
        mNone = (TextView) findViewById(R.id.myresourcer_none);
        mBack = (ImageButton) findViewById(R.id.toolbar_button1);
        mIRs = new ResourcesPresents(this,this);
        mAdapter = new ResourcesAdapter(this,mData);
        mListView.setAdapter(mAdapter);
        mBack.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
    }

    //回调获取数据
    @Override
    public void onResult(boolean flag, List<OldThing> list) {
        if(flag){
            if(list.size()<1){
                mNone.setVisibility(View.VISIBLE);
            }else{
                mData.addAll(list);
                mAdapter.notifyDataSetChanged();
            }
        }else{
            new toast(this,"数据获取失败");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OldThing oldThing = mData.get(position);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this,ResourcesDetailActivity.class);
        bundle.putSerializable(OLDTHING,oldThing);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_button1:
                finish();
                break;
        }
    }
}
