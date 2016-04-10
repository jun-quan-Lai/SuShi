package com.ljq.sushi.Service;

import com.ljq.sushi.HttpUtil.NativeHttpUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 登录注册功能接口实现
 */
public class UserServiceInterfaceIpml implements UserServiceInterface {

    @Override
    public int userLogin(HashMap<String,String> params) throws Exception {

        String jsonString = NativeHttpUtil.post(loginUrl, params);
        //Log.d("jsonString",jsonString);
        JSONObject jo = new JSONObject(jsonString);
        int  code = jo.getInt("code");
        return code;
    }

    @Override
    public int userRegist(HashMap<String, String> params) throws Exception {
        String jsonString = NativeHttpUtil.post(registUrl, params);
        JSONObject jo = new JSONObject(jsonString);
        int  code = jo.getInt("code");
        return code;
    }
}
