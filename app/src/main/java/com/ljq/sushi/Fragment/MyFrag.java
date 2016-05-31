package com.ljq.sushi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ljq.sushi.Activity.LoginActivity;
import com.ljq.sushi.Activity.RegisterActivity;
import com.ljq.sushi.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/11/8.
 */
public class MyFrag  extends Fragment implements View.OnClickListener{


    private ImageView headImg;
    private Button login;
    private Button register;

    public static MyFrag newInstance(){
        MyFrag fragment = new MyFrag();
        return fragment;
    }

    public MyFrag(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView(){
        headImg = (ImageView) getView().findViewById(R.id.myHead);
        login = (Button) getView().findViewById(R.id.login);
        register = (Button) getView().findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onClick(View v) {
       Intent intent = null;
        switch (v.getId()){
            case R.id.myHead:
                break;
            case R.id.login:
                intent = new Intent(getActivity(), LoginActivity.class);
                break;
            case R.id.register:
                intent = new Intent(getActivity(), RegisterActivity.class);
                break;

            default:
                break;
        }
        if(null!=intent)
            startActivity(intent);
    }
}
