package com.reoger.grennlife.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 24540 on 2016/9/16.
 * 通过拍摄或者从相册中寻找照片的方式得到所需要的照片
 */
public class GetPhoto {
    public final static int ACTIVITY_RESULT_CAMERA = 10010;
    public final static int ACTIVITY_RESULT_ALBUM  = 10086;
    public Uri photoUri;//图片路径URI
    private Uri tempUri;
    public File picFile;// 图片文件
    private Context context;

    public String ImageURL;

    public GetPhoto(Context context){
        this.context = context;
    }

    //选择拍照的方式
    public boolean takePhotoByCamera(){
        if (ContextCompat.checkSelfPermission(context,//动态申请照相的权限。针对6.0系统
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }
        try {
            // 创建文件夹
            File uploadFileDir = new File(
                    Environment.getExternalStorageDirectory(), "/greenLife");

            if (!uploadFileDir.exists()) {
                uploadFileDir.mkdirs();
            }
            // 创建图片，以当前系统时间命名
            ImageURL = Environment.getExternalStorageDirectory()+"/greenLife"+System.currentTimeMillis() + ".png";
            log.d("TAG",ImageURL);
            picFile = new File(uploadFileDir,
                    System.currentTimeMillis() + ".png");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            // 获取到图片路径
            tempUri = Uri.fromFile(picFile);

            // 启动Camera的Intent，传入图片路径作为存储路径
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            //启动Intent
            ((Activity) context).startActivityForResult(cameraIntent,
                    ACTIVITY_RESULT_CAMERA);

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    // 选择相册的方式
    public void takePhotobyAlbum() {
        try {
            // 创建文件夹
            File pictureFileDir = new File(
                    Environment.getExternalStorageDirectory(), "/greenLife");
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            // 创建图片，以当前系统时间命名
            picFile = new File(pictureFileDir,
                    SystemClock.currentThreadTimeMillis() + ".png");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            // 获取到图片路径
            tempUri = Uri.fromFile(picFile);

            // 获得剪辑图片的Intent
            final Intent intent = cutImageByAlbumIntent();
            ((Activity) context).startActivityForResult(intent,
                    ACTIVITY_RESULT_ALBUM);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 调用图片剪辑程序的Intent
    private Intent cutImageByAlbumIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        //设定宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设定剪裁图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    //通过相机拍照后进行剪辑
    public void cutImageByCamera() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(tempUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        ((Activity) context).startActivityForResult(intent,
                ACTIVITY_RESULT_ALBUM);
    }

    // 对图片进行编码成Bitmap
    public Bitmap decodeBitmap() {
        Bitmap bitmap = null;
        try {
            if (tempUri != null) {
                photoUri = tempUri;
                bitmap = BitmapFactory.decodeStream(context
                        .getContentResolver().openInputStream(photoUri));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }



    /**
     * <p>Activity中onActivityResult()参照以下注释代码</p>
     */


}
