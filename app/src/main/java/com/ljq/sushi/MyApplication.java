package com.ljq.sushi;

import android.app.Application;

import com.ljq.sushi.entity.UserBaseInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/3/30.
 */
public class MyApplication extends Application {

    private UserBaseInfo userBaseInfo;

    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUserBaseInfo(null);

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration =  ImageLoaderConfiguration.createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);


    }
}
