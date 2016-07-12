package com.ljq.sushi.Service;

import com.ljq.sushi.entity.UserBaseInfo;

import java.io.IOException;

/**
 * 登录注册功能接口
 */
public interface UserServiceInterface {
     UserBaseInfo userLogin(String userName, String userPwd)throws Exception;
     int userRegist(String userName,String userPwd)throws Exception;

     int changeUserBaseInfo(UserBaseInfo userBaseInfo);
     int uploadHeadImage(int userId,String path) throws IOException;
}
