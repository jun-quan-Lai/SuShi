package com.ljq.sushi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljq.sushi.Activity.LoginActivity;
import com.ljq.sushi.Activity.MedetailsActivity;
import com.ljq.sushi.Activity.RegisterActivity;
import com.ljq.sushi.MyApplication;
import com.ljq.sushi.R;
import com.ljq.sushi.entity.UserBaseInfo;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/11/8.
 */
public class MyFrag  extends Fragment implements View.OnClickListener{


    private LinearLayout nLoginTop;
    private LinearLayout loginTop;
    private ImageView headImg;
    private Button login;
    private Button register;
    private TextView myName;
    private UserBaseInfo userBaseInfo;

    public static MyFrag newInstance(){
        MyFrag fragment = new MyFrag();
        return fragment;
    }

    public MyFrag(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i("MyFrag","onCreateView");
        return inflater.inflate(R.layout.frag_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("MyFrag","onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    @Override
    public void onStart() {
        Log.i("MyFrag","onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("MyFrag","onResume");
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MyFrag","onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView(){


        nLoginTop = (LinearLayout) getView().findViewById(R.id.me_nlogin_header);
        loginTop = (LinearLayout) getView().findViewById(R.id.me_header);
        headImg = (ImageView) getView().findViewById(R.id.myHead);
        login = (Button) getView().findViewById(R.id.login);
        register = (Button) getView().findViewById(R.id.register);
        myName = (TextView) getView().findViewById(R.id.myName);

        initUser();
        headImg.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initUser(){
        final MyApplication application = (MyApplication) getActivity().getApplication();
        userBaseInfo = application.getUserBaseInfo();

        if(userBaseInfo!=null){
            nLoginTop.setVisibility(View.GONE);
            loginTop.setVisibility(View.VISIBLE);
            if(userBaseInfo.getName()!=null)
                myName.setText(userBaseInfo.getName().toString());
            if(!userBaseInfo.getHeadImg().equals("null")){
                Glide.with(MyFrag.this).load(userBaseInfo.getHeadImg()).into(headImg);
            }
        }
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
                intent = new Intent(getActivity(), MedetailsActivity.class);
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
