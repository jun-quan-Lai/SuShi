package com.ljq.sushi.Service;

import java.util.HashMap;

/**
 * 登录注册功能接口
 */
public interface UserServiceInterface {
     int userLogin(HashMap<String,String> params)throws Exception;
     int userRegist(HashMap<String,String> params)throws Exception;
}
