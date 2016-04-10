package com.ljq.sushi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ljq.sushi.Handler.MsgHandler;
import com.ljq.sushi.R;
import com.ljq.sushi.Service.UserServiceInterfaceIpml;

import java.util.HashMap;

/**
 * Created by jc on 2015/11/20.
 * 通过使用SharedPreference、Handler技术，实现显示welcome界面1.5秒
 * 与选择是否显示导航动画
 */
public class WelcomeActivity extends Activity {

    private Handler handler;
    private Message msg;
    boolean firstFlag; //是否首次安装flag
    private String userName;
    private String passWord;  //当用户选择了自动登录，用于填充账号密码
    private int httpResultcode;
    final Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler = new MsgHandler(this);
        shipToNavigationOrFrame();
    }

    //判断且实现应跳转导航动画还是主界面
    private void shipToNavigationOrFrame(){

        final SharedPreferences share = getSharedPreferences("flag", MODE_PRIVATE);
        firstFlag = share.getBoolean("first", true);
        if (firstFlag){
            intent.setClass(this,NavigationActivity.class);
            SharedPreferences.Editor editor = share.edit();
            editor.putBoolean("first", false);
            editor.apply(); //apply与commit作用相同，虽没返回值，但效率更高
        }else {//判断用户是否选择自动登录
            if(share.getBoolean("autoLogin",false)) {
                userName = share.getString("username", " ");
                passWord = share.getString("password", " ");
                final HashMap<String, String> params = new HashMap();
                params.put("userName", userName);
                params.put("userPwd", passWord);
                final UserServiceInterfaceIpml userservice = new UserServiceInterfaceIpml();
                new Thread() {
                    public void run() {
                        try {
                            httpResultcode = userservice.userLogin(params);
                            Log.d("code","code"+httpResultcode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (httpResultcode==202) {
                            intent.setClass(WelcomeActivity.this, MainActivity.class);
                        } else {
                            msg = handler.obtainMessage();
                            msg.arg1 = 2;
                            handler.sendMessage(msg);
                            intent.setClass(WelcomeActivity.this, LoginActivity.class);
                        }
                    }
                }.start();
            }
            else {
                intent.setClass(this, LoginActivity.class);
            }
        }

        new Handler().postDelayed(new Runnable() { //延时1.5秒
            @Override
            public void run() {
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 1500);
    }
}
