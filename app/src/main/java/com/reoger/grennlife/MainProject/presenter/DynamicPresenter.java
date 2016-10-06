package com.reoger.grennlife.MainProject.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.view.IDynamicView;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 24540 on 2016/9/26.
 */
public class DynamicPresenter implements IDynamicPresenter {

    private IDynamicView mIDynamicView;
    private Context mContext;
    private String bomeFileUrl;
    private Dynamic dynamic;



    private final static int UPLOAD_SUCESS = 123;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPLOAD_SUCESS:
                    dynamic.setImageUrl(bomeFileUrl);
                    dynamic.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                log.d("TAG","保存成功");
                                mIDynamicView.onPublishResult(1,s);
                            }else{
                                log.d("TAG","保存失败"+"dynamic"+e.getMessage());
                                mIDynamicView.onPublishResult(0,s);
                            }
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };



    public DynamicPresenter(Context mContext,IDynamicView mIDynamicView) {
        this.mIDynamicView = mIDynamicView;
        this.mContext = mContext;
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
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
    public void doPublish(String titile, String content, List<String> imageUrl) {

        dynamic = new Dynamic();
        dynamic.setTitle(titile);
        dynamic.setContent(content);
        UserMode user = BmobUser.getCurrentUser(UserMode.class);
        dynamic.setAuthor(user);
        if(imageUrl == null){
            dynamic.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e == null){
                        log.d("TAG","保存成功");
                        mIDynamicView.onPublishResult(1,s);
                    }else{
                        log.d("TAG","保存失败"+"dynamic"+e.getMessage());
                        mIDynamicView.onPublishResult(0,s);
                    }
                }
            });
        }else{
            String []filesPath = new String[imageUrl.size()];
            int  j=0;
            for (String item :
                    imageUrl) {
                filesPath[j] = item;
                j++;
            }
            doUploadFiles(filesPath);//上传图片到云端
        }
    }

    /**
     * 上传多张图片
     * @param filePaths
     */
    synchronized String doUploadFiles(final String[] filePaths){

        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(final List<BmobFile> files,List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                    log.d("TAG",urls.toString());
                    bomeFileUrl = urls.toString();
                    Message message = new Message();
                    message.what = UPLOAD_SUCESS;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                log.d("TAg","错误码"+statuscode +",错误描述："+errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                log.d("TAG","进度 "+curIndex+" :"+curPercent+" :"+total+": "+totalPercent);
                //这里可以用handler将进度信息传出来，反馈给用户
            }
        });

        return bomeFileUrl;
    }

    /**
     * 上传一张图片
     * @param picPath
     */
    void doUploadFile(String picPath){
        //上传图片
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    log.d("TAG","上传成功~！"+bmobFile.getFileUrl());

                }else{
                    log.d("TAG","上传失败"+e.getMessage().toString());
                }
            }
            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                log.d("TAG","文件上传进度"+0);
            }
        });
    }


}
