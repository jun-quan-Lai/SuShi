package com.ljq.sushi.Util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2015/11/9.
 */
public class Utilities {

    /**
     * 隐藏键盘
     * @param activity
     */
    static public void hideKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if(view!=null){
            ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
