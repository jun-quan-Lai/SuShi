package com.ljq.sushi.Service;

import android.util.Log;

import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.HttpUtil.NativeHttpUtil;
import com.ljq.sushi.entity.UserBaseInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 登录注册功能接口实现
 */
public class UserServiceInterfaceIpml implements UserServiceInterface {



    private int loginReturncode;

    public int getLoginReturncode() {
        return loginReturncode;
    }

    @Override
    public UserBaseInfo userLogin(String userName,String userPwd) throws Exception {

        final HashMap<String, String> params = new HashMap();
        params.put("userName", userName);
        params.put("userPwd", userPwd);
        String jsonString= NativeHttpUtil.post(AppConstants.URL_LOGIN, params);
        Log.d("登录成功后返回的东西！！！！！",jsonString);
        UserBaseInfo userBaseInfo = getUserBaseInfo(jsonString);
        if(userBaseInfo==null){
            Log.i("登录成功后返回的用户信息","我空了！！！！！！！！！！！！！！！！！！！");
        }
        return userBaseInfo;
    }

    @Override
    public int userRegist(String userName,String userPwd) throws Exception {
        int code=0;

        final HashMap<String, String> params = new HashMap();
        params.put("userName", userName);
        params.put("userPwd", userPwd);
        String jsonString = NativeHttpUtil.post(AppConstants.URL_REGIST, params);
        JSONObject jo = new JSONObject(jsonString);
        code = jo.getInt("code");
        return code;
    }

    private UserBaseInfo getUserBaseInfo(String jsonString){

        try {
            JSONObject jo = new JSONObject(jsonString);
            loginReturncode = jo.getInt("code");
            if(loginReturncode==AppConstants.OK_LOGIN){
                UserBaseInfo userBaseInfo = new UserBaseInfo();
                //JSONObject data = new JSONObject(jo.getString("data"));
                JSONArray ja = new JSONArray(jo.getString("data"));
                JSONObject data = ja.getJSONObject(0);
                userBaseInfo.setId(data.getInt("id"));
                userBaseInfo.setName(data.getString("name"));
                userBaseInfo.setEmail(data.getString("email"));
                userBaseInfo.setPhoneNo(data.getString("phoneno"));
                userBaseInfo.setSex(data.getString("sex"));
                userBaseInfo.setHeadImgUrl(data.getString("headimg"));
                userBaseInfo.setIntroduction(data.getString("introduction"));
                userBaseInfo.setMotto(data.getString("motto"));
                return userBaseInfo;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
