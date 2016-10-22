package com.reoger.grennlife.MainProject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.model.MyViewHolder;
import com.reoger.grennlife.MainProject.view.CommentActivity;
import com.reoger.grennlife.R;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.onekeyshare.OnekeyShare;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 24540 on 2016/9/15.
 * 动态的list适配器
 */
public class DynamicAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private List<Dynamic> mDatas = new ArrayList<>();
    boolean mIsLikes = false;//默认是不喜欢的
    private List<Map<Integer, Object>> likes = new ArrayList<>();

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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Dynamic dynamic = mDatas.get(position);

        if (dynamic.getAuthor() == null) {
            log.d("TAG", "可恶，竟然没有查出到数据");
        } else {
            holder.mItemContent.setText(dynamic.getContent().toString());
            holder.mItemAuthor.setText(dynamic.getAuthor().getUsername().toString());
            holder.mItemTitle.setText(dynamic.getTitle());
            holder.mItemTimeAndLocation.setText(dynamic.getCreatedAt().toString());
//            getIsLike(position);
            holder.mDynamicLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mIsLikes) {//默认为不喜欢，点击变成喜欢
                        //添加喜欢
                        holder.mDynamicLike.setImageResource(R.mipmap.like_then);//喜欢的图片，应该为
                    } else {
                        holder.mDynamicLike.setImageResource(R.mipmap.like);
                    }
                    addLike(position, mIsLikes);
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
                   Dynamic dynamic1 = mDatas.get(position);
                    doShare(dynamic1);
                }
            });
        }
    }

    private void addLike(int position, final boolean mIsLikess) {
        UserMode user = BmobUser.getCurrentUser(UserMode.class);
        Dynamic dynamic = mDatas.get(position);
        dynamic.getLikes();
        BmobRelation relation = new BmobRelation();
        if (!mIsLikess) {
            relation.add(user);
        } else {
            relation.remove(user);
            log.d("TAG", "现在是不喜欢");
        }

        dynamic.setLikes(relation);
        dynamic.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    log.d("TAG", "添加喜欢成功");
                    mIsLikes = !mIsLikess;
                } else {
                    log.d("TAG", "添加喜欢失败" + e.toString());
                }
            }
        });
    }

    /**
     * 获得当前当前用户对是否喜欢该纪录
     *
     * @return
     */
    private boolean getIsLike(final int position) {
        Dynamic dynamic = mDatas.get(position);
        BmobQuery<UserMode> query = new BmobQuery<>();
        query.addWhereNotEqualTo("likes", new BmobPointer(dynamic));
        query.findObjects(new FindListener<UserMode>() {
            @Override
            public void done(List<UserMode> list, BmobException e) {
                if (e == null) {
                    UserMode user = BmobUser.getCurrentUser(UserMode.class);
                    Map<Integer, Object> item = new HashMap<Integer, Object>();
                    if (list.contains(user)) {
                        item.put(position, true);
                        log.d("TAG", "-----哈哈 我已经点赞了");
                    } else {
                        item.put(position, false);
                        log.d("TAG", "-----哈哈 我还没赞了");
                    }
                    likes.add(item);
                } else {
                    log.d("TAG", "连这个都查询失败了，还活者干嘛" + e.toString());
                }
            }
        });

        return true;
    }


    private void doShare(Dynamic dynamic) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(dynamic.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
      //  oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(dynamic.getContent());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
       if(dynamic.getImageUrl()!= null) {
           String a = dynamic.getImageUrl();
           String b = a.substring(1, a.length() - 1);
           String ss[] = b.split(", ");
           oks.setImageUrl(ss[0]);
       }
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
       // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
      //  oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
       // oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
      //  oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(mContext);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
