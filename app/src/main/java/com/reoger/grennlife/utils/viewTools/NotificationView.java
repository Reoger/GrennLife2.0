package com.reoger.grennlife.utils.viewTools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.utils.Tools;

/**
 * Created by 24540 on 2016/10/27.
 */
public class NotificationView extends AppCompatActivity implements View.OnClickListener{
    private  String title;
    private  String content;
    private  String url;
    private ImageButton mTBack;
    private TextView mTTitle;
    private TextView mTContent;
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notification_detail);
        initData();
        initView();
        Tools tools = new Tools();
        tools.manager.cancel(Tools.NOTIFICTION_ID);
    }

    private void initView() {
        mTBack = (ImageButton) findViewById(R.id.toolbar_button1);
        mTTitle = (TextView) findViewById(R.id.notification_title);
        mTContent = (TextView) findViewById(R.id.notification_content);
        mWebView = (WebView) findViewById(R.id.notification_url);

        mTBack.setOnClickListener(this);
        mTTitle.setText(title);
        mTContent.setText(content);

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//根据传入的参数在去加载新的网页
                return true;//表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器
            }
        });
        mWebView.loadUrl(url);
    }

    private void initData() {
        Intent intent = getIntent();
         title = intent.getStringExtra(Tools.NOTIFICTION_TITLE);
         content = intent.getStringExtra(Tools.NOTIFICTION_CONTENT);
         url = intent.getStringExtra(Tools.NOTIFICTION_URL);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.all__return:
                finish();
                break;
        }
    }
}
