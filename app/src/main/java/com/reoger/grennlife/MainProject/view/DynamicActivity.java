package com.reoger.grennlife.MainProject.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.reoger.grennlife.MainProject.presenter.DynamicPresenter;
import com.reoger.grennlife.MainProject.presenter.IDynamicPresenter;
import com.reoger.grennlife.R;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 24540 on 2016/9/26.
 */
public class DynamicActivity extends AppCompatActivity implements View.OnClickListener, IDynamicView {

    private EditText mTitle;
    private EditText mContent;
    private Button mPublish;
    private GridView mPhotoView;
    private SimpleAdapter mAdapter;

    private IDynamicPresenter iDynamicPresenter;
    private List<HashMap<String, Object>> list;
    private List<String> pathList;


    private final static int REQUEST_CODE = 123;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_dynamic_news);
        initView();
        setEvents();
    }

    private void setEvents() {
        mPublish.setOnClickListener(this);
        mAdapter = new SimpleAdapter(this, getData(), R.layout.item_gridview, new String[]{"image", "text"}, new int[]{R.id.gridview_photo, R.id.gridview_text});
        /**
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
         * map.put("itemImage", R.drawable.img);
         * 解决方法:
         *              1.自定义继承BaseAdapter实现
         *              2.ViewBinder()接口实现
         *  参考 http://blog.csdn.net/admin_/article/details/7257901
         */
        mAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        mPhotoView.setAdapter(mAdapter);
        mPhotoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (list.size() == 10) { //第一张为默认图片
                    Toast.makeText(DynamicActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
                } else if (position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(DynamicActivity.this, "添加图片", Toast.LENGTH_SHORT).show();
                    //选择图片
                    iDynamicPresenter.doAddPhoto(REQUEST_CODE);
                    //通过onResume()刷新数据
                } else {
                    dialog(position);//删除照片
                }
            }
        });
    }

    private void initView() {
        mContent = (EditText) findViewById(R.id.dynamic_add_content);
        mTitle = (EditText) findViewById(R.id.dynamic_add_title);
        mPublish = (Button) findViewById(R.id.dynamic_add_publish);
        mPhotoView = (GridView) findViewById(R.id.dynamic_add_gridview);
        iDynamicPresenter = new DynamicPresenter(DynamicActivity.this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dynamic_add_publish://发布动态按钮
                if (checkVauldation()) {
                    String title = mTitle.getText().toString();
                    String content = mContent.getText().toString();
                    iDynamicPresenter.doPublish(title, content, pathList);//调用发布动态的方法
                }
                break;
        }

    }

    private boolean checkVauldation() {
        if (TextUtils.isEmpty(mTitle.getText())||TextUtils.isEmpty(mContent.getText())) {
            new toast(this, "请至少输入标题和内容");
            return false;
        }
        return true;

    }


    //刷新图片
    @Override
    protected void onResume() {
        super.onResume();
        if (pathList != null) {
            int i = 0;
            for (String path : pathList) {
                i++;
                Bitmap addbmp = BitmapFactory.decodeFile(path);
                HashMap<String, Object> map = new HashMap<>();
                map.put("image", addbmp);
                map.put("text", "第" + i + "张图片");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            pathList = null;
            pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            log.d("TAG", "直接输出" + pathList.toString());
            log.d("TAG", "\n\n");
            for (String path : pathList) {
                log.d("TAG", path);
            }
        }
    }

    public List<? extends Map<String, ?>> getData() {
        list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("image", R.mipmap.icon_addpic_focused);
        map.put("text", "添加图片");
        list.add(map);
        return list;
    }

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DynamicActivity.this);
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

    //这个回调显示发布后的结果
    @Override
    public void onPublishResult(int code, String result) {
        if (code == 1) {
            new toast(this, "发布成功" + result);
            finish();
        } else if (code == 0) {
            new toast(this, "发布失败" + result);
        } else {
            new toast(this, "未知的错误发生了，向我们反馈一解决此问题");
        }
    }
}
