package com.ljq.sushi.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.Handler.MsgHandler;
import com.ljq.sushi.MyApplication;
import com.ljq.sushi.R;
import com.ljq.sushi.Service.UserServiceInterfaceIpml;
import com.ljq.sushi.Util.UserUtil;
import com.ljq.sushi.entity.UserBaseInfo;

import java.util.Date;

public class LoginActivity extends Activity implements View.OnClickListener{
    private Handler handler;
    private Message msg;
    private String username;
    private String password;
    private UserBaseInfo userBaseInfo;

    private TextInputLayout usernameWrapper = null;
    private TextInputLayout passwordWrapper = null;
    private Button loginBtn = null;
    private Button registBtn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new MsgHandler(this);

        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registBtn = (Button) findViewById(R.id.btn_regist);

        loginBtn.setOnClickListener(this);
        registBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                username = usernameWrapper.getEditText().getText().toString().trim();
                password = passwordWrapper.getEditText().getText().toString().trim();
                if(doLogin(username,password)){
                    //把帐号信息保存到SharedPreference
                    UserUtil.saveUser(getApplicationContext(),username,password, new Date().getTime());
                }
                this.finish();
                break;
            case R.id.btn_regist:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                this.finish();
                break;
            default:
                break;
        }
    }

    /**
     * 实现用户登录功能
     * @param userName
     * @param passWord
     */
    public boolean doLogin(final String userName, final String passWord) {

        final MyApplication application = (MyApplication) getApplication();
        if (username.equals(" ")){
            usernameWrapper.setError("账号不能为空");
            passwordWrapper.setError(null);
        }
        if (password.equals(" ")){
            passwordWrapper.setError("密码不能为空");
            usernameWrapper.setError(null);
        }
        else {
            usernameWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);

            final UserServiceInterfaceIpml userservice = new UserServiceInterfaceIpml();
            Thread t = new Thread() {
                public void run() {
                    try{
                        userBaseInfo = userservice.userLogin(userName,passWord);
                       application.setUserBaseInfo(userBaseInfo); //把用户信息添加到全局
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int returncode = userservice.getLoginReturncode();
            if (returncode== AppConstants.OK_LOGIN) {
                msg = handler.obtainMessage();
                msg.arg1 = 1;
                handler.sendMessage(msg);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            } else if(returncode==AppConstants.ERROR_NAME_OR_PW){
                msg = handler.obtainMessage();
                msg.arg1 = 2;
                handler.sendMessage(msg);
            }
        }
        return false;
    }

}
