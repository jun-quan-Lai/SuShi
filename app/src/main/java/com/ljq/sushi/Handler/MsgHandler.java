package com.ljq.sushi.Handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 *处理用户需要改变UI线程的消息，避免在用户线程中改变UI线程内容
 */
public class MsgHandler extends Handler {
    private Activity activity;
    public MsgHandler(Activity activity){
        this.activity = new WeakReference<>(activity).get();//使用弱引用避免Handler内存泄漏
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
            case 3:
                showInfo("注册成功");
                break;
            case 4:
                showInfo("该手机号已经注册");
                break;
            case 5:
                showInfo("注册失败，服务器错误");
                break;
            default:
                break;
        }
    }

    public void showInfo(String info){
        Toast.makeText(activity.getApplicationContext(),info,Toast.LENGTH_SHORT).show();
    }
}
