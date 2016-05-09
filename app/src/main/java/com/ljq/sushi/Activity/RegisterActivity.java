package com.ljq.sushi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.Handler.MsgHandler;
import com.ljq.sushi.R;
import com.ljq.sushi.Service.UserServiceInterfaceIpml;

import java.util.HashMap;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener{

    private Handler handler;
    private Message msg;

    private TextInputLayout phoneNumberWrapper = null;
    private TextInputLayout passwordWrapper = null;
    private Button summitBtn = null;
    private String phoneNumber ="";
    private String password="";

    private int httpResultcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        handler = new MsgHandler(this);

        getSupportActionBar().setTitle("注册");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phoneNumberWrapper = (TextInputLayout) findViewById(R.id.phoneNumberWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.regist_passwordWrapper);
        summitBtn=(Button) findViewById(R.id.btn_summit);
        summitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_summit:
                phoneNumber = phoneNumberWrapper.getEditText().getText().toString().trim();
                password = passwordWrapper.getEditText().getText().toString().trim();
                regist(phoneNumber,password);
        }
    }

    private void regist(String phoneNumber,String password){
        if (phoneNumber.equals(" ")){
            phoneNumberWrapper.setError("手机号不能为空");
            passwordWrapper.setError(null);
        }
        else if (password.equals(" ")){
            phoneNumberWrapper.setError(null);
            passwordWrapper.setError("密码不能为空");
        }
        else if(phoneNumber.length()<11){
            phoneNumberWrapper.setError("手机号码不合法");
            passwordWrapper.setError(null);
        }
        else if(password.length()<6){
            phoneNumberWrapper.setError(null);
            passwordWrapper.setError("密码不能少于6位");
        }
        else{
            phoneNumberWrapper.setEnabled(false);
            passwordWrapper.setEnabled(false);

            final HashMap<String, String> params = new HashMap();
            params.put("userName", phoneNumber);
            params.put("userPwd", password);
            final UserServiceInterfaceIpml userservice = new UserServiceInterfaceIpml();
            new Thread() {
                public void run() {
                    try{
                        httpResultcode = userservice.userRegist(params);
                        if (httpResultcode== AppConstants.OK_REGISTER) {
                            msg = handler.obtainMessage();
                            msg.arg1 = 3;
                            handler.sendMessage(msg);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else if(httpResultcode==AppConstants.ERROR_NAME_EXIST) {
                            msg = handler.obtainMessage();
                            msg.arg1 = 4;
                            handler.sendMessage(msg);
                        }else if(httpResultcode==AppConstants.ERROR_SERVICE){
                            msg = handler.obtainMessage();
                            msg.arg1 = 5;
                            handler.sendMessage(msg);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
