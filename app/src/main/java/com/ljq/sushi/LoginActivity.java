package com.ljq.sushi;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ljq.sushi.Util.Utilities;


public class LoginActivity extends ActionBarActivity {

    private TextInputLayout usernameWrapper = null;
    private TextInputLayout passwordWrapper = null;
    private Button loginBtn = null;
    private Button registBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registBtn = (Button) findViewById(R.id.btn_regist);

        usernameWrapper.setHint("请输入帐号(adm)");
        passwordWrapper.setHint("请输入密码(adm)");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hideKeyboard();
                Utilities.hideKeyboard(LoginActivity.this);

                String username = usernameWrapper.getEditText().getText().toString();
                String password = passwordWrapper.getEditText().getText().toString();
                if (!username.equals("adm")) {
                    usernameWrapper.setError("帐号是“adm");
                } else if (!password.equals("adm")) {
                    passwordWrapper.setError("密码是”adm");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
                }
            }
        });

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    public void doLogin() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

    }
}
