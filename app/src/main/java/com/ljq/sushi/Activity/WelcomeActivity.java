package com.ljq.sushi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.Handler.MsgHandler;
import com.ljq.sushi.MyApplication;
import com.ljq.sushi.R;
import com.ljq.sushi.Service.UserServiceInterfaceIpml;
import com.ljq.sushi.Util.SharedPreferenceUtils;
import com.ljq.sushi.Util.UserUtil;
import com.ljq.sushi.entity.UserAccountInfo;
import com.ljq.sushi.entity.UserBaseInfo;

import java.util.Date;

/**
 * Created by jc on 2015/11/20.
 * 通过使用SharedPreference、Handler技术，实现显示welcome界面1.5秒
 * 与选择是否显示导航动画
 */
public class WelcomeActivity extends Activity {
    private UserBaseInfo userBaseInfo;

    private Handler handler;
    private Message msg;
    boolean firstFlag; //是否首次安装flag

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
         final MyApplication application = (MyApplication) getApplication();
        firstFlag = SharedPreferenceUtils.getBoolean(this,AppConstants.FIRST_OPEN, true);
        if (firstFlag){
            intent.setClass(this,NavigationActivity.class);
            SharedPreferenceUtils.putBoolean(this, AppConstants.FIRST_OPEN, false);
        }else {//用户不是第一次安装
            UserAccountInfo user = UserUtil.getLastLoginUser(getApplicationContext());//获取上一次登录的用户
            if(user!=null){//用户不是第一次登录

                {
                    final String userName=user.getUserName();
                    final String userPwd=user.getUserPwd();

                    final UserServiceInterfaceIpml userservice = new UserServiceInterfaceIpml();

                    Thread t =new Thread() {
                        public void run() {
                            try {
                                userBaseInfo = userservice.userLogin(userName,userPwd);
                                application.setUserBaseInfo(userBaseInfo);//添加用户信息到全局
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    t.start(); //开启线程，执行登录操作
                    try {
                        t.join();//挂起线程，等待执行结果
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int returncode= userservice.getLoginReturncode();
                    //判断登录结果
                    if (returncode ==AppConstants.OK_LOGIN) {
                        UserUtil.saveUser(getApplicationContext(),user.getUserName(),user.getUserPwd(),new Date().getTime());


                    } else {

                    }
                }
            }
            intent.setClass(this, MainActivity.class);
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
