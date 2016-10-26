package com.reoger.grennlife.MainProject.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reoger.grennlife.MainProject.adapter.DynamicAdapter;
import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.presenter.IMainPresenter;
import com.reoger.grennlife.MainProject.presenter.MainPresenterComple;
import com.reoger.grennlife.R;
import com.reoger.grennlife.encyclopaedia.view.EncyclopaediaView;
import com.reoger.grennlife.law.view.LawView;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.loginMVP.view.LoginView;
import com.reoger.grennlife.news.view.NewsView;
import com.reoger.grennlife.recyclerPlayView.adapter.BannerViewPagerAdapter;
import com.reoger.grennlife.recyclerPlayView.gear.BannerViewPager;
import com.reoger.grennlife.technology.view.TechnologyView;
import com.reoger.grennlife.upDate.presenter.PresenterCompl;
import com.reoger.grennlife.user.aboutUser.view.AboutActivity;
import com.reoger.grennlife.user.aboutUser.view.AppAboutActivity;
import com.reoger.grennlife.user.feedback.view.FeedBackActivity;
import com.reoger.grennlife.user.infomation.View.InfomationActivity;
import com.reoger.grennlife.user.monitoringHistroy.view.MonitoringHistoryView;
import com.reoger.grennlife.user.myResuouers.activity.MyResources;
import com.reoger.grennlife.user.setting.view.SettingActivity;
import com.reoger.grennlife.utils.ServerDataOperation.GlideUtil;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by 24540 on 2016/9/10.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {
    private SharedPreferences mPref;

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
    private LinearLayout mUserInfo;
    private LinearLayout mResources;
    private LinearLayout mFeedBack;
    private LinearLayout mAbout;
    private LinearLayout mAboutApp;
    private LinearLayout mSetting;

    private Button mComeEnMonitoring;
    private Button mComeRecycle;

    private ImageButton mHomeImg;
    private ImageButton mDynamicImg;
    private ImageButton mUserImg;
    private Button mBaikeBtn;
    private Button mNewsBtn;
    private Button mLawsBtn;
    private Button mTechnologyBtn;

    private ImageButton mPublishDynamic;
    private TextView mNone;


    //轮播图的图ArrayList
    private List<View> mBannerViewDatas;


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
        //自动更新检测
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (mPref.getBoolean(PresenterCompl.NET_UPDATE_AUTO_IS_CHECK, true)) {
            BmobUpdateAgent.update(this);
            new toast(this, "update");
        }
        setContentView(R.layout.layout_main);
        initActivity();
        initView();
        initEvents();

        recycleViewMethod();

    }

    private void initActivity() {
        UserMode userMode = BmobUser.getCurrentUser(UserMode.class);
        if (userMode == null) {
            startActivity(new Intent(this, LoginView.class));
            finish();
        } else {
            log.d("TAG", "已经登录过了");
        }
    }

    private void recycleViewMethod() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                initializationData();//初始化数据
            }
        }).start();


        View header = View.inflate(this, R.layout.recycle_header2_tete, null);

        mDynamicAdapter = new DynamicAdapter(MainActivity.this, mDatas);
        RecyclerViewManager.with(mDynamicAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        refreshData();
                    }

                    //下拉加载更多
                    @Override
                    public void onLoadMore() {
                        loadMoreData();
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
                case INITIALZATION://数据初始化完成
//                    new toast(getApplicationContext(), "数据加载完成");
                    if (mDatas.size() == 0) {
                        mNone.setVisibility(View.VISIBLE);
                    } else {
                        mNone.setVisibility(View.INVISIBLE);
                    }
                    break;
                case LOAD_MORE://加载更多完成
                    new toast(MainActivity.this, "加载完成");
                    break;
                case REFRESH://刷新
                    new toast(MainActivity.this, "刷新完成");
                    break;
            }
            if (mDatas.size() > 0) {
                recyclerView.onRefreshCompleted();
                mDynamicAdapter.notifyDataSetChanged();
            } else {
                new toast(MainActivity.this, "暂时没有任何记录");
            }

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
        mPublishDynamic.setOnClickListener(this);

        mComeEnMonitoring.setOnClickListener(this);
        mComeRecycle.setOnClickListener(this);

        mMonitorHistory.setOnClickListener(this);
        mUserInfo.setOnClickListener(this);
        mResources.setOnClickListener(this);
        mFeedBack.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mAboutApp.setOnClickListener(this);
        mSetting.setOnClickListener(this);

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

        BmobQuery<Dynamic> query = new BmobQuery<Dynamic>();
        query.addWhereNotEqualTo("title", "null");
        query.order("-createdAt");
        query.include("author,likes");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.setLimit(10);
        query.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        mDatas.addAll(list);
                    }
                    Message msg = new Message();
                    msg.what = INITIALZATION;
                    mHandler.sendMessage(msg);

                } else {
                    log.d("TAG", "查询失败" + e.toString() + " 原因");
                    mNone.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 加载更多
     *
     * @return
     */
    void loadMoreData() {
        int position = mDatas.size() - 1;
        if (position < 0) {
            new toast(this, "加载不可用");
        } else {
            BmobQuery<Dynamic> query = new BmobQuery<Dynamic>();
            String start = mDatas.get(position).getCreatedAt();
            log.d("TAG", "加载的最晚的时间是" + start);
            query.include("author,likes");// 希望在查询帖子信息的同时也把发布人的信息查询出来
            query.order("-createdAt");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            query.setLimit(5);//设置每次加载五条数据
            query.findObjects(new FindListener<Dynamic>() {
                @Override
                public void done(List<Dynamic> list, BmobException e) {
                    if (e == null) {
                        new toast(getApplicationContext(), "查询成功");
                        list.remove(0);
                        if (list.size() > 0) {
                            mDatas.addAll(list);
                        } else {
                            log.d("TAG", "加载更多没有加载到数据");
                        }
                        Message msg = new Message();
                        msg.what = LOAD_MORE;
                        mHandler.sendMessage(msg);
                    } else {
                        new toast(getApplicationContext(), "查询失败");
                        log.d("TAG", e.toString() + "错误码");
                    }
                }
            });

        }


    }

    /**
     * 更新数据
     */
    void refreshData() {
        if (mDatas.size() > 0) {
            BmobQuery<Dynamic> query = new BmobQuery<Dynamic>();
            String start = mDatas.get(0).getCreatedAt();
            log.d("TAG", "最新跟新的时间是" + start);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.include("author,likes");// 希望在查询帖子信息的同时也把发布人的信息查询出来,喜欢信息也需要查询出来
            query.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
            query.findObjects(new FindListener<Dynamic>() {
                @Override
                public void done(List<Dynamic> list, BmobException e) {
                    if (e == null) {
                        new toast(getApplicationContext(), "更新成功");
                        list.remove(0);
                        if (list.size() > 0) {
                            mDatas.addAll(0, list);
                        } else {
                        }
                        mDatas.addAll(list);
                        Message msg = new Message();
                        msg.what = REFRESH;
                        mHandler.sendMessage(msg);

                    } else {
                        new toast(getApplicationContext(), "查询失败");
                        log.d("TAG", "查询失败的原因" + e.toString());
                    }
                }
            });

        } else {
            log.d("TAG", "暂时还没有做这方面的加载");
        }

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

        addOneResourceToData("http://oeznvpnrn.bkt.clouddn.com/IMG_20160727_190533_107.jpg");
        addOneResourceToData("http://oeznvpnrn.bkt.clouddn.com/IMG_20160727_190606_10.jpg");
        addOneResourceToData("http://oeznvpnrn.bkt.clouddn.com/IMG_20160727_190625_980.jpg");


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

        mPublishDynamic = (ImageButton) tab02.findViewById(R.id.dynamic_add_publish_main);
        mNone = (TextView) tab02.findViewById(R.id.dynamic_item_none);

        mMonitorHistory = (LinearLayout) tab03.findViewById(R.id.user_monitoring_history);
        mUserInfo = (LinearLayout) tab03.findViewById(R.id.user_monitoring_detail);
        recyclerView = (RefreshRecyclerView) tab02.findViewById(R.id.dynamic_recyclerView);
        mResources = (LinearLayout) tab03.findViewById(R.id.user_monitoring_resources);
        mFeedBack = (LinearLayout) tab03.findViewById(R.id.user_information_feedback);
        mAbout = (LinearLayout) tab03.findViewById(R.id.use_information_about);
        mAboutApp = (LinearLayout) tab03.findViewById(R.id.user_information_app);
        mSetting = (LinearLayout) tab03.findViewById(R.id.user_information_setting);

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


    //用于轮播图增加图片用方法
    private void addOneResourceToData(int resId) {
        ImageView one = new ImageView(this);
        one.setImageResource(R.mipmap.ic_launcher);
        one.setScaleType(ImageView.ScaleType.FIT_XY);
        mBannerViewDatas.add(one);
    }


    //用于轮播图增加wangluo图片用方法
    private void addOneResourceToData(String resURL) {
        ImageView one = new ImageView(this);
        GlideUtil.loadImage(getApplicationContext(), resURL, one);
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
                Intent intent = new Intent(this, EncyclopaediaView.class);
                startActivity(intent);
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
            case R.id.user_monitoring_history://监控历史
                startActivity(new Intent(this, MonitoringHistoryView.class));
                break;
            case R.id.dynamic_add_publish_main:
                startActivity(new Intent(this, DynamicActivity.class));
                break;
            case R.id.user_monitoring_detail:
                startActivity(new Intent(this, InfomationActivity.class));
                break;
            case R.id.user_monitoring_resources://跳转到我的资源界面
                startActivity(new Intent(this, MyResources.class));
                break;
            case R.id.user_information_feedback:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.use_information_about://与我相关的界面
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.user_information_app://关于app
                startActivity(new Intent(this, AppAboutActivity.class));
                break;
            case R.id.user_information_setting://设置
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}
