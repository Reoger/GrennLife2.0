package com.reoger.grennlife.news.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by Zimmerman on 2016/10/12.
 */
public class NewsDetailView extends AppCompatActivity {
    public static final String ARG_NEWS_TITLE = "arg_news_title";
    public static final String ARG_NEWS_CONTENT = "arg_news_content";
    public static final String ARG_NEWS_OUTLINE = "arg_news_outline";

    private TextView mTitleView;
    private TextView mContentView;
    private TextView mOutlineView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_detail);

        mTitleView = (TextView) findViewById(R.id.news_detail_title);
        mContentView = (TextView) findViewById(R.id.news_detail_content);
        mOutlineView = (TextView) findViewById(R.id.news_detail_outline);

        mTitleView.setText(getIntent().getStringExtra(ARG_NEWS_TITLE));
        mContentView.setText(getIntent().getStringExtra(ARG_NEWS_CONTENT));
        mOutlineView.setText(getIntent().getStringExtra(ARG_NEWS_OUTLINE));
    }
}
