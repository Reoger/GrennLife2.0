package com.reoger.grennlife.MainProject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.reoger.grennlife.MainProject.adapter.CommentAdapter;
import com.reoger.grennlife.MainProject.adapter.DynamicAdapter;
import com.reoger.grennlife.MainProject.model.Comment;
import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.presenter.CommentPresenter;
import com.reoger.grennlife.MainProject.presenter.ICommentPresenter;
import com.reoger.grennlife.R;
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
    }

    private void initData() {
        mDynamic = (Dynamic) getIntent().getSerializableExtra(DynamicAdapter.COMMENTS);
        mICommentPresenter.doGetComment(mDynamic);//获取评论列表
        mNone.setVisibility(View.VISIBLE);
        mDynamicContent.setText(mDynamic.getContent().toString());
    }

    private void initView() {
        mDynamicContent = (TextView) findViewById(R.id.dynamic_comments_history);
        mEditComment = (EditText) findViewById(R.id.dynamic_comments_content);
        mButtonPublish = (Button) findViewById(R.id.dynamic_comments_publish);
        mListView = (ListView) findViewById(R.id.dynamic_comments_list);
        mNone = (TextView) findViewById(R.id.dynamic_comments_none);

        mICommentPresenter = new CommentPresenter(this,this);
        mAdapter = new CommentAdapter(this, commentList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dynamic_comments_publish://发表评论
                mICommentPresenter.doPublishAdapter(mDynamic,mEditComment.getText().toString());
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
        if(true){
            commentList.addAll(list);
            if(commentList.size()>0){
                mNone.setVisibility(View.INVISIBLE);
            }
            mAdapter.notifyDataSetChanged();
            log.d("TAG","數據更新成功");
        }else{
            new toast(this,"數據查詢失敗");

        }
    }
}
