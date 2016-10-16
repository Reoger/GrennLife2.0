package com.reoger.grennlife.law.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.law.model.LawsBean;

/**
 * Created by Zimmerman on 2016/10/11.
 */
public class LawDetailView extends AppCompatActivity implements ILawDetailView{
    /**
     * 需要传递过来的法律标题
     */
    public static final String ARG_LAWS_TITLE= "arg_laws_title";
    /**
     * 需要传递过来的法律内容
     */
    public static final String ARG_LAWS_CONTENT= "arg_laws_content";


    private TextView mTitle;
    private TextView mContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_laws_detail);
        mTitle = (TextView) findViewById(R.id.laws_detail_title);
        mContent = (TextView) findViewById(R.id.laws_detail_content);

        mTitle.setText(
                getIntent().getStringExtra(ARG_LAWS_TITLE)
        );
        mContent.setText(
                getIntent().getStringExtra(ARG_LAWS_CONTENT)
        );
    }

}
