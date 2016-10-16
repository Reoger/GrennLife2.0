package com.reoger.grennlife.Recycle.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.presenter.IPublishResourcesPresenter;
import com.reoger.grennlife.Recycle.presenter.PublishResourcesPresenterCompl;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 24540 on 2016/9/18.
 */
public class PublishingResourcesView extends AppCompatActivity implements View.OnClickListener,IPublishingResouresView{

    private String[] mItems;


    private Button mPublisResources;

    /**类型（旧物利用、资源回收）*/
    private String  mType;
    /**描述*/
    private EditText mContent;
    /**联系方式*/
    private EditText mPhoneNum;
    /**联系的地点*/
    private EditText mLocation;
    /**标题*/
    private EditText mTitle;

    private GridView mPhotoView;
    private SimpleAdapter mAdapter;
    private List<HashMap<String, Object>> list;
    private List<String> pathList;


    private static final int REQUEST_CODE = 0x1001;

    private IPublishResourcesPresenter mIPublishResourcesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycle_publishing);

        initView();
        initData();
        setEvent();
    }

    private void setEvent() {

        mPublisResources.setOnClickListener(this);

        mAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView imageView = (ImageView) view;
                    imageView.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        mPhotoView.setAdapter(mAdapter);
        mPhotoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.size() > 9){
                    new toast(getApplicationContext(),"图片已经满了");
                }else if(position ==0){
                    //选择图片
                    mIPublishResourcesPresenter.doAddPhoto(REQUEST_CODE);
                }else{
                    dialog(position);
                }
            }
        });
    }


    private void initView() {

        mPublisResources = (Button) findViewById(R.id.publish_resources);
        mContent = (EditText) findViewById(R.id.publish_resources_content);
        mPhoneNum = (EditText) findViewById(R.id.publish_resources_num);
        mLocation = (EditText) findViewById(R.id.publish_resources_location);
        mTitle = (EditText) findViewById(R.id.publish_resources_title);

        mPhotoView = (GridView) findViewById(R.id.public_resources_photo);
        mIPublishResourcesPresenter = new PublishResourcesPresenterCompl(PublishingResourcesView.this,this);
    }

    private void initData() {
         mItems = getResources().getStringArray(R.array.type);
        mAdapter = new SimpleAdapter(this, getData(), R.layout.item_gridview, new String[]{"image"}, new int[]{R.id.gridview_photo});

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_resources://发布资源
                mIPublishResourcesPresenter.doPublishResouerces(mTitle.getText().toString(),mContent.getText().toString(),mLocation.getText().toString()
                        ,mPhoneNum.getText().toString(),pathList);
                break;
        }
    }

    @Override
    public void onPublishResulte(boolean flag, int code) {
        if(flag){
            new toast(this,"资源发布成功");
        }else{
            new toast(this,"资源发布失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            pathList = null;
            pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            log.d("TAG","直接输出"+pathList.toString());
            log.d("TAG","\n\n");
            for (String path : pathList) {
                log.d("TAG",path);
            }
        }
    }

    //刷新图片
    @Override
    protected void onResume() {
        super.onResume();
        if (pathList != null) {
            for (String path : pathList) {
                Bitmap addbmp = BitmapFactory.decodeFile(path);
                HashMap<String, Object> map = new HashMap<>();
                map.put("image", addbmp);
                list.add(map);
                mAdapter = new SimpleAdapter(this, list, R.layout.item_gridview, new String[]{"image", "text"}, new int[]{R.id.gridview_photo, R.id.gridview_text});
                mAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {
                        if (view instanceof ImageView && data instanceof Bitmap) {
                            ImageView i = (ImageView) view;
                            i.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });
            }
            mPhotoView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            // pathList = null;
        }
    }

    /**
     * 删除选中的图片
     * @param position
     */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PublishingResourcesView.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                list.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });


        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public List<? extends Map<String, ?>> getData() {
        list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("image", R.mipmap.icon_addpic_focused);
        list.add(map);
        return list;
    }

}
