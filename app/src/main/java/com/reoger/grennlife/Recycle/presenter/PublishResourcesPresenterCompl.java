package com.reoger.grennlife.Recycle.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.Garbager;
import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.Recycle.view.IPublishingResouresView;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.squareup.picasso.Picasso.with;

/**
 * Created by 24540 on 2016/9/19.
 */
public class PublishResourcesPresenterCompl implements IPublishResourcesPresenter{

    private Context mContext;
    private IPublishingResouresView mIPublishingResouresView;

    UserMode user;

    public PublishResourcesPresenterCompl(Context context,IPublishingResouresView s) {
        this.mContext = context;
        this.mIPublishingResouresView = s;
        user = BmobUser.getCurrentUser(UserMode.class);
    }

    //添加图片
    @Override
    public void doAddPhoto(int resuletCode) {
        // 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        ImgSelActivity.startActivity((Activity) mContext,config,resuletCode);
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
            with(context).load(path).into(imageView);
        }
    };

    //发布资源
    @Override
    public void doPublishResouerces(String type,String content,String location,String time) {
        String[] list= mContext.getResources().getStringArray(R.array.type);
        log.d("TAG","bmob"+list[0]+"--"+list[1]);
        if(list[0].equals(type)){//资源回收

            Garbager garbager = new Garbager();
            garbager.setAutthor(user);
            garbager.setContent(content);
            garbager.setNum(time);
            garbager.setTitle("title");
            garbager.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.i("bmob", "保存成功");
                        mIPublishingResouresView.onPublishResulte(true,1);
                    } else {
                        Log.i("bmob", "保存失败：" + e.getMessage());
                        mIPublishingResouresView.onPublishResulte(false,1);
                    }
                }
            });
        }else if(list[1].equals(type)){//垃圾利用
            OldThing thing = new OldThing();
            thing.setAutthor(user);
            thing.setContent(content);
            thing.setNum(time);
            thing.setTitle("title");
            thing.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.i("bmob", "保存成功");
                        mIPublishingResouresView.onPublishResulte(true,2);
                    } else {
                        Log.i("bmob", "保存失败：" + e.getMessage());
                        mIPublishingResouresView.onPublishResulte(false,2);
                    }
                }
            });
        }else{
            mIPublishingResouresView.onPublishResulte(false,-1);
        }
    }
}
