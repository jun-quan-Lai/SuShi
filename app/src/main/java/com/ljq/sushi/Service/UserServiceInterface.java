package com.ljq.sushi.Service;

import java.util.HashMap;

/**
 * 登录注册功能接口
 */
public interface UserServiceInterface {
     String loginUrl = "http://114.215.99.203/sushi/login.php";
     String registUrl="http://114.215.99.203/sushi/regist.php";
     int userLogin(HashMap<String,String> params)throws Exception;
     int userRegist(HashMap<String,String> params)throws Exception;
}