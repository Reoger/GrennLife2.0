package com.reoger.grennlife.user.feedback.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.reoger.grennlife.R;
import com.reoger.grennlife.user.feedback.present.FeedBackPrenterCompl;
import com.reoger.grennlife.user.feedback.present.IFeedBackPrenter;
import com.reoger.grennlife.utils.toast;

/**
 * Created by 24540 on 2016/10/19.
 */
public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener,IFeedBack{

    private ImageButton mExit;
    private EditText mContent;
    private Button mPublish;
    private IFeedBackPrenter mIFeed;
    private EditText mEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_feedback);
        initView();
        initEvent();
    }

    private void initEvent() {
        mExit.setOnClickListener(this);
        mPublish.setOnClickListener(this);
        mIFeed = new FeedBackPrenterCompl(this,this);
    }

    private void initView() {
        mExit = (ImageButton) findViewById(R.id.toolbar_button1);
        mEmail = (EditText) findViewById(R.id.feedback_email);
        mContent = (EditText) findViewById(R.id.feedback_content);
        mPublish = (Button) findViewById(R.id.feedback_publish);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.toolbar_button1:
                finish();
                break;
            case R.id.feedback_publish:
                String content = mContent.getText().toString();
                String emali = mEmail.getText().toString();
                mIFeed.doOnPuhlish(content,emali);
                break;
        }
    }

    @Override
    public void onResultForPuhlish(boolean flag, String Code) {
        if(flag){
            dialog1_1();
        }else{
            new toast(this,"反馈失败"+Code);
        }
    }

    private void dialog1_1(){
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                      finish();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("感谢你的反馈，我们会尽快处理并与你取得联系?"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认",dialogOnclicListener);
        builder.create().show();
    }

}
