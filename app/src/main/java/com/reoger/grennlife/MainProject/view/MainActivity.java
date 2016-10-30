package com.reoger.grennlife.MainProject.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.reoger.grennlife.Recycle.model.TypeGetData;
import com.reoger.grennlife.Recycle.view.RecycleViewActivity;
import com.reoger.grennlife.encyclopaedia.view.EncyclopaediaView;
import com.reoger.grennlife.law.view.LawView;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.loginMVP.view.LoginView;
import com.reoger.grennlife.monitoring.view.EnvironmentalMonitoring;
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

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;
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
    private TextView mUserName;


    //轮播图的图ArrayList
    private List<View> mBannerViewDatas;


    private List<Dynamic> mDatas = new ArrayList<>();
    private RefreshRecyclerView recyclerView;
    private DynamicAdapter mDynamicAdapter;

    private SharedPreferences mPref;
    public final static String ACCOUNT = "account";
    public final static String PASSWORD = "password";

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
            mPref = PreferenceManager.getDefaultSharedPreferences(this);
            String account = mPref.getString(ACCOUNT, "");
            String password = mPref.getString(PASSWORD, "");
            userMode.setUsername(account);
            userMode.setPassword(password);
            userMode.login(new SaveListener<Object>() {
                @Override
                public void done(Object o, BmobException e) {
                    if(e == null ){
                        log.d("TAG","登录成功");
                    }else{
                        log.d("TAG","登录失败"+e.toString());
                    }
                }
            });
            log.d("TAG", "已经登录过了");
        }
    }

    private void recycleViewMethod() {


        View header = View.inflate(this, R.layout.recycle_header2_tete, null);
        mainPresenter.doGetData(TypeGetData.INITIALZATION);
        mDynamicAdapter = new DynamicAdapter(MainActivity.this, mDatas);
        RecyclerViewManager.with(mDynamicAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTH)
                .addHeaderView(header)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        mainPresenter.doGetData(TypeGetData.REFRESH);
                    }
                    //下拉加载更多
                    @Override
                    public void onLoadMore() {
                        mainPresenter.doGetData(TypeGetData.LOAD_MORE);
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
        UserMode uer= BmobUser.getCurrentUser(UserMode.class);
        mUserName.setText(uer.getUsername());
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
        mUserName = (TextView) tab03.findViewById(R.id.user_monitoring_username);

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
              startActivity( new Intent(MainActivity.this,EnvironmentalMonitoring.class));
                break;
            case R.id.home_resources_recycle:
                startActivity(new Intent(MainActivity.this, RecycleViewActivity.class));
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


    //回传数据到activity中
    @Override
    public void onResultGetData(boolean flag, TypeGetData data, List<Dynamic> lists) {
        if(flag){
            switch(data){
                case INITIALZATION:
                    mDatas.clear();
                    mDatas.addAll(lists);
                    break;
                case REFRESH:
                    mDatas.clear();
                    mDatas.addAll(lists);
                    break;
                case LOAD_MORE:
                    if(mDatas.size()==lists.size()){
                        new toast(this,"暂时没有更多的数据");
                    }else{
                        mDatas.clear();
                        mDatas.addAll(lists);
                    }
                    break;
            }
            mDynamicAdapter.notifyDataSetChanged();
        }else{
            new toast(this,"加载失败");
        }
        recyclerView.onRefreshCompleted();

    }
}
