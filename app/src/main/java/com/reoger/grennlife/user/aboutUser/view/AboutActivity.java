package com.reoger.grennlife.user.aboutUser.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.reoger.grennlife.MainProject.model.Comment;
import com.reoger.grennlife.R;
import com.reoger.grennlife.user.aboutUser.adapter.AboutAdapter;
import com.reoger.grennlife.user.aboutUser.presnter.AboutInfoPresentComple;
import com.reoger.grennlife.user.aboutUser.presnter.IAboutInfoPresent;
import com.reoger.grennlife.utils.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/10/19.
 */
public class AboutActivity extends AppCompatActivity implements IAboutActivity {
    private ListView mListView;
    private AboutAdapter mAdapter;
    private List<Comment> mData = new ArrayList<>();
    private IAboutInfoPresent mIAbout;
    private TextView mNone;
    private ImageButton mBack;
    private ProgressBar mBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about_me_main);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.information_listview);
        mIAbout = new AboutInfoPresentComple(this, this);
        mIAbout.doGetInVailatalData();//初始化數據
        mAdapter = new AboutAdapter(mData, this);
        mNone = (TextView) findViewById(R.id.information_none);
        mBack = (ImageButton) findViewById(R.id.toolbar_button1);
        mBar = (ProgressBar) findViewById(R.id.about_me_bar);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onResultGetData(boolean flag, List<Comment> lists) {
        if (flag) {//数据查询成功
            log.d("TAG", "与我相关的数据查询成功");
            mData.addAll(lists);
            mBar.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
            if(lists.size()==0){
                mNone.setVisibility(View.VISIBLE);
            }else{
                mNone.setVisibility(View.INVISIBLE);
            }
        } else {
            log.d("TAG", "与我相关的数据查询失败");
            mNone.setVisibility(View.VISIBLE);
        }

    }

}
