package com.reoger.grennlife.encyclopaedia.view;

import android.mtp.MtpConstants;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.reoger.grennlife.R;

/**
 * Created by Zimmerman on 2016/10/12.
 */
public class EncyclopaediaDetailView extends AppCompatActivity {
    private TextView mContentView;
    private TextView mTitleView;

    public static final String ARG_ENCYCLOPAEDIA_TITLE = "arg_encyclopaedia_title";
    public static final String ARG_ENCYLCOPAEDIA_CONTENT = "arg_encyclopaedia_content";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_encyclopaedia_detail);

        mContentView = (TextView) findViewById(R.id.encyclopaedia_detail_content);
        mTitleView = (TextView) findViewById(R.id.encyclopaedia_detail_title);

        mContentView.setText(getIntent().getStringExtra(ARG_ENCYLCOPAEDIA_CONTENT));
        mTitleView.setText(getIntent().getStringExtra(ARG_ENCYCLOPAEDIA_TITLE));
    }
}
