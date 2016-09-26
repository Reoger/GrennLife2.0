package com.reoger.grennlife.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.reoger.grennlife.R;
import com.reoger.grennlife.register.presenter.IRegisterPresenter;
import com.reoger.grennlife.register.presenter.RegisterPresenter;
import com.reoger.grennlife.utils.toast;

/**
 * Created by 24540 on 2016/9/10.
 */
public class RegisterView extends AppCompatActivity implements IRegisterView,View.OnClickListener{

    private IRegisterPresenter mIRegisterPresenter;

    private EditText mRegisterUserName;
    private EditText mRegsiterNum;
    private EditText mRegisterMSMCode;
    private EditText mRegisterPasswword;

    private ImageButton mRegsiterGetMSMCode;
    private Button mRegsiter;
    private Button mRegsiterClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_main);

        initView();

        mRegsiterClear.setOnClickListener(this);
        mRegsiter.setOnClickListener(this);
        mRegsiterGetMSMCode.setOnClickListener(this);
    }

    private void initView() {
        mIRegisterPresenter = new RegisterPresenter(this);

        mRegisterMSMCode = (EditText) findViewById(R.id.register_check_code);
        mRegisterPasswword = (EditText) findViewById(R.id.register_password);
        mRegsiterNum = (EditText) findViewById(R.id.register_num);
        mRegisterUserName = (EditText) findViewById(R.id.register_username);
        mRegsiterGetMSMCode = (ImageButton) findViewById(R.id.register_getMSMCheckCode);
        mRegsiter = (Button) findViewById(R.id.register_load);
        mRegsiterClear = (Button) findViewById(R.id.register_clear);
    }

    @Override
    public void clear() {
        mRegisterMSMCode.setText("");
        mRegisterPasswword.setText("");
        mRegsiterNum.setText("");
        mRegisterUserName.setText("");
        mRegisterUserName.setText("");
    }

    @Override
    public void regsiterResult(Boolean result, String code) {
        if(result){
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                mIRegisterPresenter.doLoginWithPassword(mRegsiterNum.getText().toString(),mRegisterPasswword.getText().toString());
        }else{
            Toast.makeText(this,"注册失败,信息为"+code,Toast.LENGTH_SHORT).show();
            mIRegisterPresenter.doClear();
        }
    }

    @Override
    public void getMSMCodeResult(Boolean flag, String h) {
        if(flag){
            Toast.makeText(this,"短信发送成功，请注意查收",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"短信发送失败"+h,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_getMSMCheckCode://发送短信
                mIRegisterPresenter.doGetCheckMSMCode(mRegsiterNum.getText().toString());
                break;
            case R.id.register_load://注册
                mIRegisterPresenter.doRegister(mRegisterUserName.getText().toString(),
                        mRegsiterNum.getText().toString(),mRegisterMSMCode.getText().toString(),
                        mRegisterPasswword.getText().toString());
                break;
            case R.id.register_clear:
                mIRegisterPresenter.doClear();
                break;
        }
    }
}
