package com.ljq.sushi;

import android.content.Context;

import com.ljq.sushi.entity.UserBaseInfo;
import com.ljq.sushi.loader.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;


/**
 * Created by Administrator on 2016/3/30.
 */
public class MyApplication extends android.app.Application {

    private static Context context;

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
        context = this.getApplicationContext();
        setUserBaseInfo(null);

        //配置GallerFinal主题
        ThemeConfig theme = ThemeConfig.GREEN;
        //配置GallerFinal功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setForceCrop(true)
                .setEnablePreview(true)
        .build();

        //配置GallerFinal的imageloader
        cn.finalteam.galleryfinal.ImageLoader imageloader = new GlideImageLoader();
        //设置GallerFinal核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)
        .build();
        GalleryFinal.init(coreConfig);

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration =  ImageLoaderConfiguration.createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);



    }

}
