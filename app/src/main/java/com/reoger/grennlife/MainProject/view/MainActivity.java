package com.reoger.grennlife.MainProject.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.reoger.grennlife.MainProject.adapter.DynamicAdapter;
import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.presenter.IMainPresenter;
import com.reoger.grennlife.MainProject.presenter.MainPresenterComple;
import com.reoger.grennlife.R;
import com.reoger.grennlife.encyclopaedia.view.EncyclopaediaView;
import com.reoger.grennlife.law.view.LawView;
import com.reoger.grennlife.news.view.NewsView;
import com.reoger.grennlife.recyclerPlayView.adapter.BannerViewPagerAdapter;
import com.reoger.grennlife.recyclerPlayView.gear.BannerViewPager;
import com.reoger.grennlife.technology.view.TechnologyView;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/10.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews = new ArrayList<View>();
    private IMainPresenter mainPresenter;

    private LinearLayout mTabHome;
    private LinearLayout mTabDynamic;
    private LinearLayout mTabUser;
    //轮播图界面与adapter
    private BannerViewPager mBannerView;
    private BannerViewPagerAdapter mBannerAdapter;
    private LinearLayout mMonitorHistory;

    private Button mComeEnMonitoring;
    private Button mComeRecycle;

    private ImageButton mHomeImg;
    private ImageButton mDynamicImg;
    private ImageButton mUserImg;
    private Button mBaikeBtn;
    private Button mNewsBtn;
    private Button mLawsBtn;
    private Button mTechnologyBtn;

    private ProgressDialog mDialog;
    //轮播图的图ArrayList
    private List<View> mBannerViewDatas;
//    private ArrayList<String> mDatas;


    private List<Dynamic> mDatas = new ArrayList<>();
    private RefreshRecyclerView recyclerView;
    private DynamicAdapter mDynamicAdapter;


    private final static int INITIALZATION = 0x10;
    private final static int LOAD_MORE = 0x11;
    private final static int REFRESH = 0x12;
    private final static int INITIALZATION_FINISH = 0x20;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initView();
        initEvents();

        recycleViewMethod();

    }

    private void recycleViewMethod() {

        showDialog();
        initializationData();//初始化数据
        testLALAL();

        View header = View.inflate(this, R.layout.recycle_header2, null);
        View footer = View.inflate(this, R.layout.dynamic_botton, null);

        mDynamicAdapter = new DynamicAdapter(MainActivity.this, mDatas);
        RecyclerViewManager.with(mDynamicAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .addFooterView(footer)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        Message msg = new Message();
                        msg.what = REFRESH;
                        mHandler.sendMessageDelayed(msg, 1000);
                    }

                    //下拉加载更多
                    @Override
                    public void onLoadMore() {
                        Message msg = new Message();
                        msg.what = LOAD_MORE;
                        mHandler.sendMessageDelayed(msg, 1000);
                    }
                })
//                .setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
//                android.widget.Toast.makeText(MainActivity.this, "item" + position, android.widget.Toast.LENGTH_SHORT).show();
//            }
//        })//这里是针对item的点击事件
                .into(recyclerView, this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INITIALZATION_FINISH://数据初始化完成
                    new toast(getApplicationContext(), "数据加载完成");
                    mDialog.dismiss();
                    break;
                case LOAD_MORE://加载更多
                    testLALAL();
                    break;
                case REFRESH://刷新
                    testLALAL();
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
        mLawsBtn.setOnClickListener(this);
        mTechnologyBtn.setOnClickListener(this);

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

    /**
     * 初始化数据
     *
     * @return
     */
    void initializationData() {

        BmobQuery<Dynamic> query = new BmobQuery<>();
        query.addWhereNotEqualTo("title", "null");
        query.setLimit(10);
        query.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e == null) {
                    mDatas.addAll(list);
                    Message msg = new Message();
                    msg.what = INITIALZATION_FINISH;
                    mHandler.sendMessage(msg);
                } else {
                    log.d("TAG", "查询失败");
                }
            }
        });

    }

    /**
     * 加载更多
     *
     * @return
     */
    List<Dynamic> loadMoreData() {
        List<Dynamic> list = new ArrayList<>();
        BmobQuery<Dynamic> query = new BmobQuery<>();

        return list;
    }

    /**
     * 更新数据
     */
    List<Dynamic> refreshData() {
        List<Dynamic> list = new ArrayList<>();
        BmobQuery<Dynamic> query = new BmobQuery<>();

        return list;
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
        View tab01 = mInflater.inflate(R.layout.layout_home_main, null);//主界面
        View tab02 = mInflater.inflate(R.layout.layout_base_main, null);//动态界面
        View tab03 = mInflater.inflate(R.layout.layout_user_main, null);//用户界面

        /**
         * 轮播图控件初始化
         */
        mBannerView = (BannerViewPager) tab01.findViewById(R.id.home_en_recycler_play_view);
        mBannerViewDatas = new ArrayList<>();
        addOneResourceToData(R.drawable.recycler_play_a);
        addOneResourceToData(R.drawable.recycler_play_b);
        addOneResourceToData(R.drawable.recycler_play_c);
        addOneResourceToData(R.drawable.picture_);
//        addOneResourceToData(R.drawable.picture_2);
        mBannerAdapter = new BannerViewPagerAdapter(mBannerViewDatas);
        mBannerView.setAdapter(mBannerAdapter);
        mBannerAdapter.notifyDataSetChanged();
        //位于home的button
        mBaikeBtn = (Button) tab01.findViewById(R.id.home_en_baike);
        mNewsBtn = (Button) tab01.findViewById(R.id.home_en_news);
        mLawsBtn = (Button) tab01.findViewById(R.id.home_en_laws);
        mTechnologyBtn = (Button) tab01.findViewById(R.id.home_en_technology);


        mComeEnMonitoring = (Button) tab01.findViewById(R.id.home_en_control);
        mComeRecycle = (Button) tab01.findViewById(R.id.home_resources_recycle);

        mMonitorHistory = (LinearLayout) tab03.findViewById(R.id.user_monitoring_history);

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


//    private void initData() {
//        mImageData = new ArrayList<>();
//        addOneResourceToData(R.drawable.a);
//        addOneResourceToData(R.drawable.b);
//        addOneResourceToData(R.drawable.c);
//        addOneResourceToData(R.drawable.d);
//        addOneResourceToData(R.drawable.e);
//    }

    //用于轮播图增加图片用方法
    private void addOneResourceToData(int resId) {
        ImageView one = new ImageView(this);
        one.setImageResource(resId);
        one.setScaleType(ImageView.ScaleType.FIT_XY);
        mBannerViewDatas.add(one);
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
                mainPresenter.doComeActivity(MainActivity.this, MainPresenterComple.MONITORING);
                break;
            case R.id.home_resources_recycle:
                mainPresenter.doComeActivity(MainActivity.this, MainPresenterComple.RECYCLE);
                break;
            case R.id.home_en_baike:
                Log.d("debug", "baike btn");
                Intent intent = new Intent(this, EncyclopaediaView.class);
                startActivity(intent);
            case R.id.user_monitoring_history:
                break;
            case R.id.home_en_news:
                Intent newsIntent = new Intent(this, NewsView.class);
                startActivity(newsIntent);
                break;
            case R.id.home_en_laws:
                Intent lawsIntent = new Intent(this, LawView.class);
                startActivity(lawsIntent);
                break;
            case R.id.home_en_technology:
                Intent technologyIntent = new Intent(this, TechnologyView.class);
                startActivity(technologyIntent);
                break;

        }
    }

    public void addNewsDynamic(View view) {
        new toast(this, "点击事件测试");
        startActivity(new Intent(this, DynamicActivity.class));
    }


    private void showDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle("Loading...");
        mDialog.setMessage("正在加载中，请稍后...");
        mDialog.setCancelable(true);
        mDialog.show();
    }

    private void testLALAL() {
        Dynamic item = new Dynamic();
        item.setContent("sds");
        item.setTitle("123");
        item.setImageUrl("[http://bmob-cdn-6268.b0.upaiyun.com/2016/10/05/17bf2a2321db4d008dc7b20fc3b1e755.png, http://bmob-cdn-6268.b0.upai");
        mDatas.add(item);
    }
}
