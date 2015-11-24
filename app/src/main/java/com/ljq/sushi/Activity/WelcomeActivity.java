package com.ljq.sushi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Handler;

import com.ljq.sushi.R;

/**
 * Created by jc on 2015/11/20.
 * 通过使用SharedPreference、Handler技术，实现显示welcome界面1.5秒
 * 与选择是否显示导航动画
 */
public class WelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        shipToNavigationOrFrame();
    }

    //判断且实现应跳转导航动画还是主界面
    private void shipToNavigationOrFrame(){
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        boolean firstFlag; //是否首次安装flag
        SharedPreferences sharedPreferences = getSharedPreferences("flag", MODE_PRIVATE);
        firstFlag = sharedPreferences.getBoolean("first", true);

        final Intent intent = new Intent();
        if (firstFlag){
            intent.setClass(this,NavigationActivity.class);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first", false);
            editor.apply(); //apply与commit作用相同，虽没返回值，但效率更高
        }else {
            intent.setClass(this, MainActivity.class);
        }
        new Handler().postDelayed(new Runnable() { //延时1.5秒
            @Override
            public void run() {
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        },1500);
    }
}
