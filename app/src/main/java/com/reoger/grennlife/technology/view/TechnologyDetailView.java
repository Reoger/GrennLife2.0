package com.reoger.grennlife.technology.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.PublicHolder;

/**
 * Created by Zimmerman on 2016/10/12.
 */
public class TechnologyDetailView extends AppCompatActivity {
    private TextView mContentView;
    private TextView mTitleView;

    public static final String ARG_TECHNOLOGY_CONTENT = "arg_technology_content";
    public static final String ARG_TECHNOLOGY_TITLE = "arg_technology_title";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_technology_detail);

        mContentView = (TextView) findViewById(R.id.technology_detail_content);
        mTitleView = (TextView) findViewById(R.id.technology_detail_title);

        mContentView.setText(getIntent().getStringExtra(ARG_TECHNOLOGY_CONTENT));
        mTitleView.setText(getIntent().getStringExtra(ARG_TECHNOLOGY_TITLE));

    }
}
