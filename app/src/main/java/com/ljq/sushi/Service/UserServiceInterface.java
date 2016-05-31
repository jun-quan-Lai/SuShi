package com.ljq.sushi.Service;

import com.ljq.sushi.entity.UserBaseInfo;

/**
 * 登录注册功能接口
 */
public interface UserServiceInterface {
     UserBaseInfo userLogin(String userName, String userPwd)throws Exception;
     int userRegist(String userName,String userPwd)throws Exception;
}
