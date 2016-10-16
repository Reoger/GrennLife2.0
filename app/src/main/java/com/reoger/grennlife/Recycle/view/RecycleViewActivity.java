package com.reoger.grennlife.Recycle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.presenter.IRecyclingOldPresenter;
import com.reoger.grennlife.Recycle.presenter.RecyclingOldPresenterCompl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 24540 on 2016/9/18.
 */
public class RecycleViewActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton mAddMsg;
    private IRecyclingOldPresenter mIRecyclingOldPresenter;


    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = Arrays.asList("垃圾回收", "旧物利用");

    private ViewPagerIndicator mIndicator;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycle_main);

        initView();
        initDatas();
        setonClickListen();

        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager,0);

    }

    private void initDatas() {

        GarbagerFragment tab01 = new GarbagerFragment();
        OldThingFragment tab02 = new OldThingFragment();

        mTabContents.add(tab01);
        mTabContents.add(tab02);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position)
            {
                return mTabContents.get(position);
            }
        };
    }

    private void setonClickListen() {
        mAddMsg.setOnClickListener(this);

    }

    private void initView() {

        mIRecyclingOldPresenter = new RecyclingOldPresenterCompl(this);
        mAddMsg = (ImageButton) findViewById(R.id.recycle_add);
        ///ttt
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.recycle_add://添加资源信息
                mIRecyclingOldPresenter.doComeActivity(1);
                break;
        }
    }
}
