package com.reoger.grennlife.utils.ServerDataOperation;

import android.content.Context;
import android.text.Layout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.reoger.grennlife.R;

/**
 * Created by admin on 2016/10/9.
 */
public class GlideUtil {
    public static void loadImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.wait_holder)
                .into(imageView);
    }

}
