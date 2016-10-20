package com.reoger.grennlife.monitoring.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.monitoring.presenter.IMonitoringPresenter;
import com.reoger.grennlife.monitoring.presenter.MonitoringPresenterCompl;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 24540 on 2016/9/11.
 */
public class EnvironmentalMonitoring extends AppCompatActivity implements IEnvironmentalMonitoring, View.OnClickListener {


    private static final String TAG = "EnvironmentalMonitoring";
    private IMonitoringPresenter mIMonitoringPresenter;

    private GridView mPhotoView;
    private SimpleAdapter mAdapter;

    private List<HashMap<String, Object>> list;
    private List<String> pathList;

    private EditText mLocation;
    private EditText mTitle;
    private EditText mContent;
    private EditText mNum;
    private ImageButton mBack;

    private static final int REQUEST_CODE = 0x12;

    private Button mMonitoring;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_monitoring_main);
        initView();
        initEvents();
    }

    private void initEvents() {
        mIMonitoringPresenter = new MonitoringPresenterCompl(this,EnvironmentalMonitoring.this);
        mIMonitoringPresenter.doGetCurrentLocation(this);
        mBack.setOnClickListener(this);

        mMonitoring.setOnClickListener(this);

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
                    Toast.makeText(EnvironmentalMonitoring.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
                } else if (position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(EnvironmentalMonitoring.this, "添加图片", Toast.LENGTH_SHORT).show();
                    //选择图片
                    mIMonitoringPresenter.doAddPhoto(REQUEST_CODE);

                } else {
                    dialog(position);//删除照片
                }
            }
        });


    }

    private void initView() {
        mLocation = (EditText) findViewById(R.id.monitoring_location);
        mTitle = (EditText) findViewById(R.id.monitoring_title);
        mContent = (EditText) findViewById(R.id.monitoring_content);
        mNum = (EditText) findViewById(R.id.monitoring_num);
        mBack = (ImageButton) findViewById(R.id.toolbar_button1);

        mPhotoView = (GridView) findViewById(R.id.monitoring_photo);


        mMonitoring = (Button) findViewById(R.id.monitoring_button);

    }

    @Override
    public void onClear() {
        mContent.setText("");
        mTitle.setText("");
        mNum.setText("");
        mLocation.setText("");
    }

    /**
     * 显示当前位置信息
     *
     * @param location
     */
    @Override
    public void onGetCurrentLocation(final String location) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLocation.setText(location);
            }
        });

    }

    @Override
    public void onLoadResult(int Code, String stats) {
        if(Code == 1){
            new toast(this,"信息上传成功，请等待审核结果~");
        }else if(Code == 2){
            new toast(this,"信息上传成功，请等待审核结果~");
            log.d("TAG",stats);
        }else{
            new toast(this,"未知的错误发生了，请与我们联系以解决问题");
        }
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monitoring_button://上传举报信息
                if(checkValidation()){
                    String title = mTitle.getText().toString();
                    String content = mContent.getText().toString();
                    String num = mNum.getText().toString();
                    String location = mLocation.getText().toString();
                    mIMonitoringPresenter.doUploadMonitoringInfo(title,content,num,location,pathList);
                }
                break;
            case R.id.toolbar_button1:
                finish();
        }
    }

    private boolean checkValidation() {
        if(TextUtils.isEmpty(mTitle.getText())){
            new toast(this,"标题不能为空");
            return false;
        }else if(TextUtils.isEmpty(mContent.getText())){
            new toast(this,"内容不能为空");
            return false;
        }else if(TextUtils.isEmpty(mNum.getText())){
            new toast(this,"电话号码不能为空");
            return false;
        }else if(TextUtils.isEmpty(mLocation.getText())){
            new toast(this,"地点不能为空");
            return false;
        }else if(mNum.getText().toString().length()!=11){
            new toast(this,"请输入正确的手机号");
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EnvironmentalMonitoring.this);
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
        map.put("text", "添加图片");
        list.add(map);
        return list;
    }


}
