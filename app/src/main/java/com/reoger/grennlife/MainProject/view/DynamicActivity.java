package com.reoger.grennlife.MainProject.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.reoger.grennlife.MainProject.presenter.DynamicPresenter;
import com.reoger.grennlife.MainProject.presenter.IDynamicPresenter;
import com.reoger.grennlife.R;
import com.reoger.grennlife.utils.log;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 24540 on 2016/9/26.
 */
public class DynamicActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mTitle;
    private EditText mContent;
    private Button mAddPhoto;
    private Button mPublish;

    private GridView mPhotoView;
    private SimpleAdapter mAdapter;

    private IDynamicPresenter iDynamicPresenter;
    private ArrayList<HashMap<String,Object>> list ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_dynamic_news);
        initView();
        setEvents();
    }

    private void setEvents() {
        mPublish.setOnClickListener(this);
        mAddPhoto.setOnClickListener(this);
        mAdapter = new SimpleAdapter(this,getData(),R.layout.item_gridview,new String[]{"image","text"},new int[]{R.id.gridview_photo,R.id.gridview_text});
        mPhotoView.setAdapter(mAdapter);
    }

    private void initView() {
        mContent = (EditText) findViewById(R.id.dynamic_add_content);
        mTitle = (EditText) findViewById(R.id.dynamic_add_title);
        mPublish = (Button) findViewById(R.id.dynamic_add_publish);
        mAddPhoto  = (Button) findViewById(R.id.dynamic_add_photo);
        mPhotoView = (GridView) findViewById(R.id.dynamic_add_gridview);

        iDynamicPresenter = new DynamicPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamic_add_publish://发布动态按钮

                break;
            case R.id.dynamic_add_photo://添加图片
                iDynamicPresenter.doAddPhoto(123);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            for (String path : pathList) {
                mContent.append(path + "\n");
                // Picasso.with(this).load(path).into(mImage);
                Bitmap bit = BitmapFactory.decodeFile(path);
                if(bit == null){
                    log.d("TAg","加载错误");
                }else{
                    log.d("Tag","加载成功~");
                }



            }
            //设置图片显示
            mAdapter.notifyDataSetChanged();
        }
    }


    public List<? extends Map<String,?>> getData() {
        list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("image",R.mipmap.ic_launcher);
            map.put("text",i+"text");
            list.add(map);
        }
        return list;
    }
}
