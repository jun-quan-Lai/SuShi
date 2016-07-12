package com.ljq.sushi.Service;

import android.util.Log;

import com.google.gson.Gson;
import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.HttpUtil.NativeHttpUtil;
import com.ljq.sushi.entity.UserBaseInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
        int code;

        final HashMap<String, String> params = new HashMap();
        params.put("userName", userName);
        params.put("userPwd", userPwd);
        String jsonString = NativeHttpUtil.post(AppConstants.URL_REGIST, params);
        JSONObject jo = new JSONObject(jsonString);
        code = jo.getInt("code");
        return code;
    }



    /**
     * 提交用户修改的个人信息
     * @param userBaseInfo
     * @return
     */
    @Override
    public int changeUserBaseInfo(UserBaseInfo userBaseInfo) {
        int code =1;
        String returnStr;
        final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        Gson gson = new Gson();
        String jsonString = gson.toJson(userBaseInfo);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("userInfo",jsonString)
                .build();
        Request request = new Request.Builder()
                .url(AppConstants.URL_CHANGE_USERINFO)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            else{
                JSONObject jsonObject = new JSONObject(response.body().string());
                code = jsonObject.getInt("code");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }


    @Override
    public int uploadHeadImage(int userId, String path) throws IOException {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        final OkHttpClient client = new OkHttpClient();

        File file = new File(path);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", Integer.toString(userId))
                .addFormDataPart("image", "img.jpg", RequestBody.create(MEDIA_TYPE_PNG, file))//一定要文件名！！！
                .build();

            Request request = new Request.Builder()
                    .url(AppConstants.URL_CHANGE_USERHEADIMG)
                    .post(requestBody)
                    .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();


        return 0;
    }


    /**
     * 从服务器返回的Json中获取用户基本信息，包装成UserBaseInfo
     * @param jsonString
     * @return
     */
    public UserBaseInfo getUserBaseInfo(String jsonString){

        try {
            JSONObject jo = new JSONObject(jsonString);
            loginReturncode = jo.getInt("code");
            if(loginReturncode==AppConstants.OK_LOGIN){
                UserBaseInfo userBaseInfo = new UserBaseInfo();
                JSONArray ja = new JSONArray(jo.getString("data"));
                JSONObject data = ja.getJSONObject(0);
                userBaseInfo.setId(data.getInt("id"));
                userBaseInfo.setName(data.getString("name"));
                userBaseInfo.setEmail(data.getString("email"));
                userBaseInfo.setPhoneNo(data.getString("phoneno"));
                userBaseInfo.setSex(data.getString("sex"));
                userBaseInfo.setHeadImg(data.getString("headimg"));
                userBaseInfo.setLastHeadImgTime(data.getString("lastheadimgtime"));
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
