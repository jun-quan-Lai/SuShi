package com.ljq.sushi.Activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ljq.sushi.Handler.MsgHandler;
import com.ljq.sushi.R;
import com.ljq.sushi.Service.UserServiceInterfaceIpml;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener{
    private Handler handler;
    private Message msg;
    private String username;
    private String password;
    private  int httpResultcode;

    private TextInputLayout usernameWrapper = null;
    private TextInputLayout passwordWrapper = null;
    private Button loginBtn = null;
    private Button registBtn = null;
    private CheckBox rememberPswChBx = null;
    private CheckBox autoLoginChBx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new MsgHandler(this);

        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registBtn = (Button) findViewById(R.id.btn_regist);
        rememberPswChBx = (CheckBox) findViewById(R.id.rememberPswChBx);
        rememberPswChBx.setChecked(false);
        autoLoginChBx = (CheckBox) findViewById(R.id.autoLoginChBx);
        autoLoginChBx.setChecked(false);
        rememberPswChBx.setOnCheckedChangeListener(checkboxlister);
        autoLoginChBx.setOnCheckedChangeListener(checkboxlister);

        loginBtn.setOnClickListener(this);
        registBtn.setOnClickListener(this);

        SharedPreferences share = getSharedPreferences("flag", MODE_PRIVATE);
        if (share.getBoolean("rememberPsw", false)) {
            usernameWrapper.getEditText().setText(share.getString("username", " "));
            passwordWrapper.getEditText().setText(share.getString("password", " "));
            rememberPswChBx.setChecked(true);
        }
        else{
            usernameWrapper.setHint("请输入帐号");
            passwordWrapper.setHint("请输入密码");
        }
    }

    private CheckBox.OnCheckedChangeListener checkboxlister = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences share1 = getSharedPreferences("flag", MODE_PRIVATE);
            SharedPreferences.Editor editor = share1.edit();
            if (rememberPswChBx.isChecked()) {
                editor.putString("username", usernameWrapper.getEditText().getText().toString().trim());
                editor.putString("password", passwordWrapper.getEditText().getText().toString().trim());
                editor.putBoolean("rememberPsw",true);
            }
            if(autoLoginChBx.isChecked()){
                editor.putBoolean("autoLogin", true);
            }
            else{
                editor.putBoolean("autoLogin", false);
            }
            editor.commit();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                username = usernameWrapper.getEditText().getText().toString().trim();
                password = passwordWrapper.getEditText().getText().toString().trim();
                doLogin(username,password);
                break;
            case R.id.btn_regist:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
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
    public void doLogin(String userName, String passWord) {
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

            final HashMap<String, String> params = new HashMap();
            params.put("username", userName);
            params.put("userpwd", passWord);
            final UserServiceInterfaceIpml userservice = new UserServiceInterfaceIpml();
            new Thread() {
                public void run() {
                    try{
                        httpResultcode = userservice.userLogin(params);
                        if (httpResultcode==200) {
                            msg = handler.obtainMessage();
                            msg.arg1 = 1;
                            handler.sendMessage(msg);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            msg = handler.obtainMessage();
                            msg.arg1 = 2;
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
