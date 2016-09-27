package com.reoger.grennlife.MainProject.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.reoger.grennlife.MainProject.adapter.DynamicAdapter;
import com.reoger.grennlife.MainProject.presenter.IMainPresenter;
import com.reoger.grennlife.MainProject.presenter.MainPresenterComple;
import com.reoger.grennlife.R;
import com.reoger.grennlife.encyclopaedia.view.EncyclopaediaView;
import com.reoger.grennlife.news.view.NewsView;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/10.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,IMainActivity {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews = new ArrayList<View>();
    private IMainPresenter mainPresenter;

    private LinearLayout mTabHome;
    private LinearLayout mTabDynamic;
    private LinearLayout mTabUser;

    private Button mComeEnMonitoring;
    private Button mComeRecycle;

    private ImageButton mHomeImg;
    private ImageButton mDynamicImg;
    private ImageButton mUserImg;
    private Button mBaikeBtn;
    private Button mNewsBtn;


    private ArrayList<String> mDatas;
    private RefreshRecyclerView recyclerView;
    private DynamicAdapter mDynamicAdapter;
    private int counts = 10;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initView();
        initEvents();

        recycleViewMethod();

    }

    private void recycleViewMethod() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("我真的很需要一个人在我身边，就是那种天天带我学习，张嘴闭嘴就是要考证，约我出去都是要去图书馆学习，看我不学习反手就给我一巴掌的引领我走向人生巅峰的那种人"+i);
        }
        View header = View.inflate(this,R.layout.recycle_header2,null);
        View footer = View.inflate(this,R.layout.dynamic_botton,null);
        mDynamicAdapter = new DynamicAdapter(this,mDatas);
        RecyclerViewManager.with(mDynamicAdapter,new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .addFooterView(footer)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        Message msg = new Message();
                        msg.what =10;
                        mHandler.sendMessageDelayed(msg,2000);
                    }
                    //下拉刷新
                    @Override
                    public void onLoadMore() {

                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                android.widget.Toast.makeText(MainActivity.this, "item" + position, android.widget.Toast.LENGTH_SHORT).show();
            }
        }).into(recyclerView,this);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    mDatas.add(0, "罗杰真丑"+"1123");
                    break;
                case 23:
                    for (int i = 0; i < 10; i++){
                        mDatas.add("item" + (counts + i));
                    }
                    counts += 10;
                    break;
            }
            recyclerView.onRefreshCompleted();
            mDynamicAdapter.notifyDataSetChanged();
        }
    };

    private void initEvents() {

        mTabHome.setOnClickListener(this);
        mTabDynamic.setOnClickListener(this);
        mTabUser.setOnClickListener(this);

        mBaikeBtn.setOnClickListener(this);
        mNewsBtn.setOnClickListener(this);

        mComeEnMonitoring.setOnClickListener(this);
        mComeRecycle.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                resetImg();
                switch (currentItem) {
                    case 0:
                        mHomeImg.setImageResource(R.mipmap.home_bright);
                        break;
                    case 1:
                        mDynamicImg.setImageResource(R.mipmap.circle_bright);
                        break;
                    case 2:
                        mUserImg.setImageResource(R.mipmap.my_bright);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);

        mTabHome = (LinearLayout) findViewById(R.id.main_bottom_home);
        mTabDynamic = (LinearLayout) findViewById(R.id.main_bottom_dynamic);
        mTabUser = (LinearLayout) findViewById(R.id.main_bottom_user);

        mHomeImg = (ImageButton) findViewById(R.id.main_bottom_home_img);
        mDynamicImg = (ImageButton) findViewById(R.id.main_bottom_dynamic_img);
        mUserImg = (ImageButton) findViewById(R.id.main_bottom_user_img);



        mainPresenter = new MainPresenterComple(this);

        mHomeImg.setImageResource(R.mipmap.home_bright);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View tab01 = mInflater.inflate(R.layout.layout_home_main, null);
        View tab02 = mInflater.inflate(R.layout.layout_base_main, null);
        View tab03 = mInflater.inflate(R.layout.layout_user_main, null);

        //位于home的button
        mBaikeBtn = (Button) tab01.findViewById(R.id.home_en_baike);
        mNewsBtn = (Button) tab01.findViewById(R.id.home_en_news);



        mComeEnMonitoring = (Button) tab01.findViewById(R.id.home_en_control);
        mComeRecycle = (Button) tab01.findViewById(R.id.home_resources_recycle);

        recyclerView = (RefreshRecyclerView) tab02.findViewById(R.id.dynamic_recyclerView);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);



        mAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        mViewPager.setAdapter(mAdapter);

    }

    /**
     * 将所有的图片切换为暗色的
     */
    private void resetImg() {
        mDynamicImg.setImageResource(R.mipmap.circle_dark);
        mUserImg.setImageResource(R.mipmap.my_dark);
        mHomeImg.setImageResource(R.mipmap.home_test);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bottom_home:
                mViewPager.setCurrentItem(0);
                mHomeImg.setImageResource(R.mipmap.home_bright);
                break;
            case R.id.main_bottom_dynamic:
                mViewPager.setCurrentItem(1);
                mDynamicImg.setImageResource(R.mipmap.circle_bright);
                break;
            case R.id.main_bottom_user:
                mViewPager.setCurrentItem(2);
                mUserImg.setImageResource(R.mipmap.my_bright);
                break;
            case R.id.home_en_control:
                mainPresenter.doComeActivity(MainActivity.this,MainPresenterComple.MONITORING);
                break;
            case R.id.home_resources_recycle:
                mainPresenter.doComeActivity(MainActivity.this,MainPresenterComple.RECYCLE);
                break;
            case R.id.home_en_baike:
                Log.d("debug","baike btn");
                Intent intent = new Intent(this, EncyclopaediaView.class);
                startActivity(intent);
                break;
            case R.id.home_en_news:
                Intent newsIntent = new Intent(this, NewsView.class);
                startActivity(newsIntent);
                break;

        }
    }

    public void addNewsDynamic(View view){
        new toast(this,"点击事件测试");
        startActivity(new Intent(this,DynamicActivity.class));
    }

}
