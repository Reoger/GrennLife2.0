package com.reoger.grennlife.MainProject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.reoger.grennlife.MainProject.adapter.DynamicAdapter;
import com.reoger.grennlife.MainProject.model.Comment;
import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.R;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 24540 on 2016/10/5.
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mDynamicContent;
    private EditText mEditComment;
    private Button mButtonPublish;

    private Dynamic Dynamic;



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
    }

    private void initData() {
         Dynamic = (Dynamic) getIntent().getSerializableExtra(DynamicAdapter.COMMENTS);
        mDynamicContent.setText(Dynamic.getContent().toString());
    }

    private void initView() {
        mDynamicContent = (TextView) findViewById(R.id.dynamic_comments_history);
        mEditComment = (EditText) findViewById(R.id.dynamic_comments_content);
        mButtonPublish = (Button) findViewById(R.id.dynamic_comments_publish);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamic_comments_publish://发表评论(需要封装)
                UserMode use = BmobUser.getCurrentUser(UserMode.class);
                final Comment comment = new Comment();
                comment.setContent(mEditComment.getText().toString());
                comment.setUser(use);
                comment.setDynamic(Dynamic);
                comment.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                       if(e == null){
                           log.d("TAG","评论成功");
                           finish();
                       }else{
                           log.d("TAG","评论失败");
                       }
                    }
                });
                break;
        }
    }
}
