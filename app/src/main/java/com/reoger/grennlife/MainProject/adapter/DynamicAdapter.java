package com.reoger.grennlife.MainProject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.model.MyViewHolder;
import com.reoger.grennlife.MainProject.view.CommentActivity;
import com.reoger.grennlife.R;
import com.reoger.grennlife.onekeyshare.OnekeyShare;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by 24540 on 2016/9/15.
 * 动态的list适配器
 */
public class DynamicAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private List<Dynamic> mDatas = new ArrayList<>();

    public static final String COMMENTS = "comments";

    public DynamicAdapter(Context contex, List<Dynamic> datas) {
        mContext = contex;
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mItemContent.setText(mDatas.get(position).getContent().toString());
        // holder.mItemAuthor.setText(mDatas.get(position).getAuthor().toString());
        // holder.mItemAuthor.setText(mDatas.get(position).getAuthor().toString());
        holder.mDynamicLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new toast(mContext, "点击了喜欢" + position);
            }
        });
        holder.mDynamicComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new toast(mContext, "评论ing");
                Bundle bundle = new Bundle();
                bundle.putSerializable(COMMENTS, mDatas.get(position));
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.mDynamicShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享按钮的结果
                showShare();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private void showShare() {
//        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(getString(R.string.share));
        oks.setTitle(mContext.getResources().getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("这是用户：xxx分享的东西啊");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(mContext.getResources().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(mContext);
    }


}
