package com.ljq.sushi;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.ljq.sushi.Util.Validate;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputLayout usernameWrapper=(TextInputLayout)findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper=(TextInputLayout)findViewById(R.id.passwordWrapper);
        final Button btn=(Button)findViewById(R.id.btn_login);

        usernameWrapper.setHint("请输入帐号");
        passwordWrapper.setHint("请输入密码");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                String username=usernameWrapper.getEditText().getText().toString();
                String password=passwordWrapper.getEditText().getText().toString();
                if(!Validate.isEmail(username)){
                    usernameWrapper.setError("Not a valid emial addres!");
                }else if(!Validate.isPassword(password)){
                    passwordWrapper.setError("Not a valid password!");
                }else{
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
                }
            }
        });
    }

    private void hideKeyboard(){
        View view = getCurrentFocus();
        if(view!=null){
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void doLogin(){
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
    }
}
