package com.ljq.sushi.Service;


import com.ljq.sushi.HttpUtil.NativeHttpUtil;

import java.util.HashMap;

/**
 * 登录注册功能接口实现
 */
public class UserServiceInterfaceIpml implements UserServiceInterface {

    @Override
    public String userLogin(String url,HashMap<String,String> params) throws Exception {

        String result = NativeHttpUtil.post(url, params);
        if(!result.isEmpty()){
            return result;
        }
        else return null;

    }
}
