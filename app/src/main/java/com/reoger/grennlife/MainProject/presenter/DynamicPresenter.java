package com.reoger.grennlife.MainProject.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import static com.squareup.picasso.Picasso.with;

/**
 * Created by 24540 on 2016/9/26.
 */
public class DynamicPresenter implements IDynamicPresenter {

    private Context mContext;

    public DynamicPresenter(Context mContext) {
        this.mContext = mContext;
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
            with(context).load(path).into(imageView);
        }
    };

    //添加图片描述
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
        ImgSelActivity.startActivity((Activity) mContext, config, resuletCode);
    }

    //发布
    @Override
    public void doPublish() {

    }
}
