package com.reoger.grennlife.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;

import com.reoger.grennlife.R;
import com.reoger.grennlife.utils.viewTools.NotificationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 24540 on 2016/9/14.
 */
public class Tools {
    /**
     * 保存图片到/sdcard/greenLife/目录下
     *
     * @param bmp
     * @return
     */
    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "greenLife");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        return fileName;
    }

    /***
     * 上传文件到bmob
     *
     * @param picPath
     * @return
     */
    public String upLoadFile(final String picPath) {

        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    //toast("上传文件成功:" + bmobFile.getFileUrl());
                    // return bmobFile.getFileUrl().toString();
                    //  urlImage = bmobFile.getFileUrl().toString();
                    log.d("TAG", "上传成功~！" + bmobFile.getFileUrl());
                } else {
                    // toast("上传文件失败：" + e.getMessage());
                    // return e.getMessage().toString();
                    //  urlImage = e.getMessage().toString();
                    log.d("TAG", "上传失败" + e.getMessage().toString());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                log.d("TAG", "文件上传进度" + value);
            }
        });
        return "";
    }

    /**
     * 获取图片的真实路径
     *
     * @param context
     * @param uri
     * @param seletion
     * @return
     */
    public String getImagePath(Context context, Uri uri, String seletion) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;
    }


    public static final String NOTIFICTION_TITLE = "notificationTile";
    public static final String NOTIFICTION_CONTENT = "notificationContent";
    public static final String NOTIFICTION_URL = "notificationUrl";
    public static final int NOTIFICTION_ID = 0x110;
    public NotificationManager manager;

    public void showNotification(Context context, String title, String content, final String url) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, NotificationView.class);
        intent.putExtra(NOTIFICTION_TITLE, title);
        intent.putExtra(NOTIFICTION_CONTENT, content);
        intent.putExtra(NOTIFICTION_URL, url);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 110, intent,0);

        Notification notification = builder
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.smile)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(), R.mipmap.logo))
                .build();
        manager.notify(NOTIFICTION_ID, notification);

    }
}
