package com.reoger.grennlife.Recycle.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reoger.grennlife.Recycle.model.OldThing;
import com.reoger.grennlife.Recycle.view.IPublishingResouresView;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by 24540 on 2016/9/19.
 */
public class PublishResourcesPresenterCompl implements IPublishResourcesPresenter{

    private Context mContext;
    private String bomeFileUrl;
    private IPublishingResouresView mIPublishingResouresView;
    private OldThing oldThing;

    UserMode user;

    private final static int UPLOAD_SUCESS = 123;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPLOAD_SUCESS:
                    oldThing.setImageUrl(bomeFileUrl);
                    oldThing.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                log.d("TAG","保存成功");
                                mDialog.dismiss();
                                mIPublishingResouresView.onPublishResulte(true, 1);
                            }else{
                                log.d("TAG","保存失败"+"Dynamic"+e.getMessage());
                                mDialog.dismiss();
                                mIPublishingResouresView.onPublishResulte(false, 0);

                            }
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };


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
                .needCrop(false)
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
            Glide.with(context).load(path).into(imageView);
        }
    };

    //发布资源
    @Override
    public void doPublishResouerces(String title,String content,String location,String time,List<String> imageUrl) {
        showDialog();
        oldThing = new OldThing();
        oldThing.setAuthor(user);
        oldThing.setContent(content);
        oldThing.setNum(time);
        oldThing.setTitle(title);
        oldThing.setAvailable(true);
        oldThing.setLocations(location);

        if (imageUrl == null){
            oldThing.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.i("bmob", "保存成功");
                        mDialog.dismiss();
                        mIPublishingResouresView.onPublishResulte(true, 1);
                    } else {
                        Log.i("bmob", "保存失败：" + e.getMessage());
                        mDialog.dismiss();
                        mIPublishingResouresView.onPublishResulte(false, 1);
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

    private ProgressDialog mDialog;

    private void showDialog() {
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle("publish...");
        mDialog.setMessage("正在登录，请稍后...");
        mDialog.setCancelable(true);
        mDialog.show();
    }
}
