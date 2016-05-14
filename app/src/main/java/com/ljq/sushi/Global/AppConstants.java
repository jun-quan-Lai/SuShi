package com.ljq.sushi.Global;

/**
 * Created by Administrator on 2016/5/1.
 */
public class AppConstants {
    public static final String FIRST_OPEN = "first_open";

    public static final String URL_LOGIN = "http://114.215.99.203/app/Home/Me/login";
    public static final String URL_REGIST="http://114.215.99.203/app/Home/Me/regist";

    /*
    get方式传type参数，不传type参数会返回所有文章
带参数形式：http://114.215.99.203/app/Home/Baike/getArticles/type/菜谱
     */
    public static final String URL_ARTICLE="http://114.215.99.203/app/Home/Baike/getArticles";

    public static final String URL_RESTAURANT="http://114.215.99.203/app/Home/Restaurant/getRestaurants/city/东莞";

    //服务器返回的状态码
    public static final int OK = 203;
    public static final int OK_REGISTER = 201;
    public static final int OK_LOGIN = 202;
    public static final int ERROR_NAME_EXIST = 402;
    public static final int ERROR_NAME_OR_PW = 404;
    public static final int ERROR_SERVICE = 403;


}
