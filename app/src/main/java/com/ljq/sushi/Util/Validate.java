package com.ljq.sushi.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/3.
 */
public class Validate {


    /**
     * 验证email地址的正则表达式
     */
    private static final String EMAIL_PATTERN="^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

    private static Pattern pattern = null;
    private static Matcher matcher = null;

    public static  boolean isEmail(String email){
        pattern=Pattern.compile(EMAIL_PATTERN);
        matcher=pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPassword(String password) {
        return password.length() > 5;
    }
}
