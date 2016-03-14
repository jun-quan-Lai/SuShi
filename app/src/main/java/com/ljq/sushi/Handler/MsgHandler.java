package com.ljq.sushi.Handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/3/12.
 */
public class MsgHandler extends Handler {
    private Activity activity;
    public MsgHandler(Activity activity){
        this.activity = new WeakReference<Activity>(activity).get();//使用弱引用避免Handler内存泄漏
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1){
            case 1:
                showInfo("登录成功");
                break;
            case 2:
                showInfo("用户名或密码错误，请重新输入");
                break;
            default:
                break;
        }
    }

    public void showInfo(String info){
        Toast.makeText(activity.getApplicationContext(),info,Toast.LENGTH_SHORT).show();
    }
}
