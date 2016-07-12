package com.ljq.sushi.Global;

/**
 * Created by Administrator on 2016/5/1.
 */
public class AppConstants {
    public static final String FIRST_OPEN = "first_open";//应用第一次安装标记，存进SharedPreferences用

    public static final String URL_LOGIN = "http://114.215.99.203/app/Home/User/login";
    public static final String URL_REGIST="http://114.215.99.203/app/Home/User/regist";
    public static final String URL_GET_USERBASEINFO = "http://114.215.99.203/app/Home/User/returnUserInfo/";
    public static final String URL_CHANGE_USERINFO="http://114.215.99.203/app/Home/User/changeUserInfo";
    public static final String URL_CHANGE_USERHEADIMG = "http://114.215.99.203/app/Home/User/changeUserHeadImg";

    /*
    get方式传type参数，不传type参数会返回所有文章
带参数形式：http://114.215.99.203/app/Home/Baike/getArticles/type/菜谱
     */
    public static final String URL_ARTICLE="http://114.215.99.203/app/Home/Baike/getArticles";

    public static final String URL_RESTAURANT="http://114.215.99.203/app/Home/Restaurant/getRestaurants/city/";


    /*
    从MainActivity调用(RestaurantFrag中出现)startActivityForResult()
    去请求更多城市时的请求代码
     */
    public static final int CITY_REQUEST = 1;

    /*
    城市Activity被finish后成功返回城市的返回代码
     */
    public static final int CITY_RESULT_SUCCESS = 1;

    //服务器返回的状态码

    public static final int OK_REGISTER = 201;
    public static final int OK_LOGIN = 202;
    public static final int OK_GET_ARTICLE = 203;
    public static final int OK_GET_RESTAURANT = 204;
    public static final int OK_CHANGE_USERINFO = 205;


    public static final int ERROR_NAME_EXIST = 402;
    public static final int ERROR_NAME_OR_PW = 404;
    public static final int ERROR_SERVICE = 403;
    public static final int WITHOUT_RESTAURANT = 405;
    public static final int ERROR_CHANGE_USERINFO = 406;


}
