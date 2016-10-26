package com.reoger.grennlife.user.setting.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reoger.grennlife.loginMVP.view.LoginView;
import com.reoger.grennlife.onekeyshare.OnekeyShare;
import com.reoger.grennlife.utils.toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.update.AppVersion;

/**
 * Created by Zimmerman on 2016/10/19.
 */
public class SettingCompl implements ISettingView {
    private Context mContext;

    public SettingCompl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void logOut() {
        //清除缓存用户对象
        if (BmobUser.getCurrentUser() != null) {
            BmobUser.logOut();
            new toast(mContext, "退出登录！");
            Intent loginIntent = new Intent(mContext, LoginView.class);
            mContext.startActivity(loginIntent);
            ((Activity) mContext).finish();

        } else {
            new toast(mContext, "未登录！");
        }
    }

    @Override
    public void doAppShare(Context mContext) {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享了黄福金和他的朋友们自己做的的应用“greenlife”给你");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn(titleURL)");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("这是一个关于共同构造更好生活环境的APP，有了她地球就会更美好一些");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://oeznvpnrn.bkt.clouddn.com/wait_holder.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://env.people.com.cn/GB/1075/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK(SITE)");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(mContext);
    }

}
