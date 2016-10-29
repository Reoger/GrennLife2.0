package com.reoger.grennlife.MainProject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reoger.grennlife.MainProject.adapter.CommentAdapter;
import com.reoger.grennlife.MainProject.adapter.DynamicAdapter;
import com.reoger.grennlife.MainProject.model.Comment;
import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.presenter.CommentPresenter;
import com.reoger.grennlife.MainProject.presenter.ICommentPresenter;
import com.reoger.grennlife.R;
import com.reoger.grennlife.utils.CustomApplication;
import com.reoger.grennlife.utils.log;
import com.reoger.grennlife.utils.toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/10/5.
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener, ICommentView {

    private TextView mDynamicContent;
    private EditText mEditComment;
    private Button mButtonPublish;
    private ICommentPresenter mICommentPresenter;
    private Dynamic mDynamic;

    private ListView mListView;
    private CommentAdapter mAdapter;
    private TextView mNone;

    private TextView mTitle;
    private TextView mTime;
    private TextView mUsername;
    private ImageButton mBack;
    private GridLayout mGridLayout;
    private ProgressBar mProgressBar;

    private List<Comment> commentList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_dynamic_comment);
        initView();
        initData();
        setEvent();


    }

    private void setEvent() {
        mButtonPublish.setOnClickListener(this);
        mListView.setAdapter(mAdapter);
        mBack.setOnClickListener(this);
    }

    private void initData() {
        mDynamic = (Dynamic) getIntent().getSerializableExtra(DynamicAdapter.COMMENTS);
        mICommentPresenter.doGetComment(mDynamic);//获取评论列表
        mProgressBar.setVisibility(View.VISIBLE);
        mNone.setVisibility(View.INVISIBLE);
        mDynamicContent.setText(mDynamic.getContent().toString());
        mTime.setText(mDynamic.getCreatedAt().toString());
        mUsername.setText("by: "+mDynamic.getAuthor().getUsername());
        mTitle.setText(mDynamic.getTitle());
        if(mDynamic.getImageUrl()!= null){
            mGridLayout.removeAllViews();
            String a = mDynamic.getImageUrl();
            String b = a.substring(1,a.length()-1);
            log.d("TAG","TTT"+b);
            String[] bb = b.split(", ");
            log.d("TAG","这里是图片的url"+mDynamic.getObjectId()+":"+a);
            for(int i=0;i<bb.length;i++){
                ImageView imageView = new ImageView(this);
                Glide.with(this).load(bb[i])
                        .placeholder(R.mipmap.photo)//设置占位图片
                        .error(android.R.drawable.stat_notify_error)//图片加载失败的显示
//                        .bitmapTransform()//剪成正方形
                        .crossFade()//设置淡入淡出效果
//                        .override(300,300)//设置图片大小
                        .into(imageView);
                        imageView.setPadding(0,0,10,10);

                mGridLayout.addView(imageView);
                final String picPath = bb[i];
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CommentActivity.this,ShowPicView.class);
                        i.putExtra("Pic",picPath);
                        startActivity(i);
                    }
                });
                GridLayout.LayoutParams para;
                para = (GridLayout.LayoutParams) imageView.getLayoutParams();
                if(para != null) {
                    CustomApplication application = CustomApplication.getApplication();
                    para.height = (application.getmWidth()-30)/3;
                    para.width = (application.getmWidth()-30)/3;
                    imageView.setLayoutParams(para);
                }
            }
        }

    }

    private void initView() {

        mDynamicContent = (TextView) findViewById(R.id.dynamic_comments_history);
        mEditComment = (EditText) findViewById(R.id.dynamic_comments_content);
        mButtonPublish = (Button) findViewById(R.id.dynamic_comments_publish);
        mListView = (ListView) findViewById(R.id.dynamic_comments_list);
        mNone = (TextView) findViewById(R.id.dynamic_comments_none);
        mTitle = (TextView) findViewById(R.id.dynamic_comments_title);
        mUsername = (TextView) findViewById(R.id.dynamic_comments_name);
        mTime = (TextView) findViewById(R.id.dynamic_comments_time);
        mBack = (ImageButton) findViewById(R.id.toolbar_button1);
        mGridLayout = (GridLayout) findViewById(R.id.dynamic_comments_gridlayout);
        mProgressBar = (ProgressBar) findViewById(R.id.dynamic_add_progress_bar);


        mICommentPresenter = new CommentPresenter(this,this);
        mAdapter = new CommentAdapter(this, commentList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dynamic_comments_publish://发表评论
                mICommentPresenter.doPublishAdapter(mDynamic,mEditComment.getText().toString());
                break;
            case R.id.toolbar_button1:
                finish();
                break;
        }
    }

    @Override
    public void onResultPublishComment(boolean flag, String code) {
            if(flag){
                new toast(this,"發表評論成功");
            }else{
                new toast(this,"發表評論失敗"+code);
            }
        finish();
    }

    @Override
    public void onResultGetComment(boolean flag, List<Comment> list) {
        mProgressBar.setVisibility(View.GONE);
        if(flag){
            if(list.size()>0){
                commentList.addAll(list);
                mNone.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
            log.d("TAG","數據更新成功");
        }else{
            new toast(this,"數據查詢失敗");
        }
    }
}
