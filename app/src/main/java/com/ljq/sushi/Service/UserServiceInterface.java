package com.ljq.sushi.Service;

import java.util.HashMap;

/**
 * 登录注册功能接口
 */
public interface UserServiceInterface {
     String userLogin(String url,HashMap<String,String> params)throws Exception;
}
