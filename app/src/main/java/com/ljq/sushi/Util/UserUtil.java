package com.ljq.sushi.Util;

import android.content.Context;

import com.ljq.sushi.entity.UserAccountInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装操作用户相关的方法
 */
public class UserUtil {

    /**
     * 获得所用用户信息
     * @return
     */
    public static List<UserAccountInfo> getUserAccountInfos(Context context)
    {
        List<UserAccountInfo> userAccountInfos = new ArrayList<>();
        String userinfos = SharedPreferenceUtils.getString(context,"users", "");// 取得所有用户帐号信息
        // 获得用户字串
        if (userinfos != "")// 有数据
        {
            // name1/pwd1/logintime,name2/pwd2/logintime
            if (userinfos.contains(","))// 判断有无, 逗号代表每个用户分割点
            {
                String[] users = userinfos.split(",");
                for (String str : users)
                {
                    UserAccountInfo userAccountInfo = new UserAccountInfo();
                    String[] user = str.split("/");
                    userAccountInfo.setUserName(user[0]); // 用户名
                    userAccountInfo.setUserPwd(user[1]); // 密码
                    userAccountInfo.setLoginTime(Long.parseLong(user[2]));
                    userAccountInfos.add(userAccountInfo);
                }
            } else
            // 没有, 代表只有一个用户
            {
                UserAccountInfo userAccountInfo = new UserAccountInfo();
                String[] user = userinfos.split("/");
                userAccountInfo.setUserName(user[0]); // 用户名
                userAccountInfo.setUserPwd(user[1]); // 密码
                userAccountInfo.setLoginTime(Long.parseLong(user[2]));
                userAccountInfos.add(userAccountInfo);
            }
            return userAccountInfos;
        } else
        {
            return null;
        }
    }

    /**
     * 返回APP最后一次登录的用户，下次进入APP时自动登录这个用户的帐号
     * @return
     */
    public static UserAccountInfo getLastLoginUser(Context context){
        List<UserAccountInfo> users = getUserAccountInfos(context);
        if(users==null){
            return null;
        }
        UserAccountInfo lastLoginUser = users.get(0);
        long maxTime = users.get(0).getLoginTime();
        for(UserAccountInfo user : users){
            if(user.getLoginTime() > maxTime)
                lastLoginUser = user;
        }
        return lastLoginUser;
    }


    /**
     * 保存新用户信息或者更新用户登录时间
     * @param userName
     * @param userPwd
     * @param loginTime
     */
    public static void saveUser(Context context, String userName, String userPwd, long loginTime)
    {
        String userinfos = "";
        List<UserAccountInfo> userAccountInfos = getUserAccountInfos(context);
        if(userAccountInfos ==null){
            userinfos = userName + "/" + userPwd + "/" + loginTime;
        }else{
            for (UserAccountInfo user : userAccountInfos)
            {
                String uname = user.getUserName();
                String pwd ;
                long time;
                //若用户已存在，则更新
                if (uname.equals(userName)){
                    uname=userName;
                    pwd = userPwd;
                    time = loginTime;
                }
                else{
                    uname=userName;
                    pwd = userPwd;
                    time=user.getLoginTime();
                }
                String userinfo = uname + "/" + pwd + "/" +time;
                if (userinfos == "")
                {
                    userinfos = userinfo;
                } else
                {
                    userinfos += "," + userinfo;
                }
            }
        }
        SharedPreferenceUtils.putString(context,"users",userinfos);
    }
}
