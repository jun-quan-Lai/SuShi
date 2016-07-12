package com.ljq.sushi.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ljq.sushi.MyApplication;
import com.ljq.sushi.R;
import com.ljq.sushi.Service.UserServiceInterfaceIpml;
import com.ljq.sushi.Util.Validate;
import com.ljq.sushi.entity.UserBaseInfo;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class MedetailsActivity extends AppCompatActivity implements View.OnClickListener{


    private LinearLayout changeImg;
    private LinearLayout introduction;
    private  LinearLayout name;
    private LinearLayout sex;
    private LinearLayout phoneNo;
    private LinearLayout email;
    private LinearLayout motto;

    private ImageView head;

    private TextView txt_introduction;
    private TextView txt_name;
    private TextView txt_sex;
    private TextView txt_phoneNo;
    private TextView txt_email;
    private TextView txt_motto;

     //private Spinner sexSpinner;

    private UserBaseInfo userBaseInfo;

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    final UserServiceInterfaceIpml userservice = new UserServiceInterfaceIpml();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medetails);
        initView();
        Log.d("MedetailsActivity","onCreate");
        setUserInfo();
    }


    private void initView() {

        head = (ImageView) findViewById(R.id.userInfo_headimg);
        changeImg = (LinearLayout) findViewById(R.id.userInfo_changeHeadimg);
        introduction = (LinearLayout) findViewById(R.id.userInfo_introduction);
        name = (LinearLayout) findViewById(R.id.userInfo_name);
        sex = (LinearLayout) findViewById(R.id.userInfo_sex);
        phoneNo = (LinearLayout) findViewById(R.id.userInfo_phoneNo);
        email = (LinearLayout) findViewById(R.id.userInfo_email);
        motto = (LinearLayout) findViewById(R.id.userInfo_motto);

        txt_introduction = (TextView) findViewById(R.id.userInfo_introduction_text);
        txt_name = (TextView) findViewById(R.id.userInfo_name_text);
        txt_sex = (TextView) findViewById(R.id.userInfo_sex_text);
        txt_phoneNo = (TextView) findViewById(R.id.userInfo_phone_text);
        txt_email = (TextView) findViewById(R.id.userInfo_email_text);
        txt_motto = (TextView) findViewById(R.id.userInfo_motto_text);

        changeImg.setOnClickListener(this);
        introduction.setOnClickListener(this);
        name.setOnClickListener(this);
        sex.setOnClickListener(this);
        phoneNo.setOnClickListener(this);
        email.setOnClickListener(this);
        motto.setOnClickListener(this);
    }

    private void setUserInfo() {
        final MyApplication application = (MyApplication) getApplication();
        userBaseInfo = application.getUserBaseInfo();
        if(!userBaseInfo.getIntroduction().equals("null")){
            txt_introduction.setText(userBaseInfo.getIntroduction());
        }
        if(!userBaseInfo.getName().equals("null")){
            txt_name.setText(userBaseInfo.getName());
        }
        if(!userBaseInfo.getSex().equals("null")){
            txt_sex.setText(userBaseInfo.getSex());
        }
        if(!userBaseInfo.getPhoneNo().equals("null")){
            txt_phoneNo.setText(userBaseInfo.getPhoneNo());
        }
        if(!userBaseInfo.getEmail().equals("null")){
            txt_email.setText(userBaseInfo.getEmail());
        }
        if(!userBaseInfo.getMotto().equals("null")){
            txt_motto.setText(userBaseInfo.getMotto());
        }

        if(!userBaseInfo.getHeadImg().equals("null")){
            Glide.with(this).load(userBaseInfo.getHeadImg()).into(head);
        }
    }

    /**
     * 更改MyApplication中全局用户信息
     * @param userBaseInfo
     */
    private void changeUserInfo(final UserBaseInfo userBaseInfo){
        final MyApplication application = (MyApplication) getApplication();

        application.setUserBaseInfo(userBaseInfo);

        Thread t = new Thread(){
            public void run(){
                userservice.changeUserBaseInfo(userBaseInfo);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        final EditText ed;

        switch (v.getId()){
            case R.id.userInfo_changeHeadimg:
                showPhotopicker();
                break;
            case R.id.userInfo_introduction:
                ed = new EditText(this);
                ed.setText(txt_introduction.getText().toString());
                new AlertDialog.Builder(this).setTitle("简介").setView(
                        ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(userBaseInfo.getIntroduction()!=ed.getText().toString()){
                            userBaseInfo.setIntroduction(ed.getText().toString());
                            txt_introduction.setText(ed.getText().toString());
                            changeUserInfo(userBaseInfo);
                        }
                    }
                }).setNegativeButton("取消", null).show();

                break;
            case R.id.userInfo_name:
                 ed = new EditText(this);
                ed.setText(txt_name.getText().toString());
                new AlertDialog.Builder(this).setTitle("姓名").setView(
                        ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(userBaseInfo.getName()!=ed.getText().toString()){
                            userBaseInfo.setName(ed.getText().toString());
                            txt_name.setText(ed.getText().toString());
                            changeUserInfo(userBaseInfo);
                        }

                    }
                })
                .setNegativeButton("取消", null).show();

                break;
            case R.id.userInfo_sex:
                LinearLayout sp = (LinearLayout) getLayoutInflater()
                        .inflate(R.layout.sex_spinner, null);
                final Spinner sexSpinner = (Spinner) sp.findViewById(R.id.sex_spinner);
                sp.removeView(sexSpinner); //从原来的LinearLayout中移除，才能绑定到AlertDialog
                if(txt_sex.getText().toString().equals("男"))
                    sexSpinner.setSelection(1);
                else
                    sexSpinner.setSelection(2);

                new AlertDialog.Builder(this).setTitle("性别").setView(
                        sexSpinner).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sex = sexSpinner.getSelectedItemPosition()==1 ? "男":"女";
                        if(userBaseInfo.getSex()!=sex){
                            userBaseInfo.setSex(sex);
                            txt_sex.setText(sex);
                            changeUserInfo(userBaseInfo);
                        }

                    }
                }).show();

                break;
            case R.id.userInfo_phoneNo:
                ed = new EditText(this);
                ed.setText(txt_phoneNo.getText().toString());
                new AlertDialog.Builder(this).setTitle("手机号").setView(
                        ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phone = ed.getText().toString();
                        if(userBaseInfo.getPhoneNo()!=phone && phone.length()==11){
                            userBaseInfo.setPhoneNo(phone);
                            txt_phoneNo.setText(phone);
                            changeUserInfo(userBaseInfo);
                        }

                    }
                }).setNegativeButton("取消", null).show();

                break;
            case R.id.userInfo_email:
                ed = new EditText(this);
                ed.setText(txt_email.getText().toString());
                new AlertDialog.Builder(this).setTitle("邮箱").setView(
                        ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = ed.getText().toString();
                        if(userBaseInfo.getEmail()!=email && Validate.isEmail(email)){
                            userBaseInfo.setEmail(email);
                            txt_email.setText(email);
                            changeUserInfo(userBaseInfo);
                        }

                    }
                }).setNegativeButton("取消", null).show();

                break;
            case R.id.userInfo_motto:
                ed = new EditText(this);
                ed.setText(txt_motto.getText().toString());
                new AlertDialog.Builder(this).setTitle("个性签名").setView(
                        ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(userBaseInfo.getMotto() != ed.getText().toString()){
                            userBaseInfo.setMotto(ed.getText().toString());
                            txt_motto.setText(ed.getText().toString());
                            changeUserInfo(userBaseInfo);
                        }
                    }
                }).setNegativeButton("取消", null).show();

                break;
            default:
                break;
        }
    }


    private void showPhotopicker(){
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY,  mOnHanlderResultCallback);
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);


                Thread t = new Thread() {
                    public void run() {
                        try{
                            userservice.uploadHeadImage(userBaseInfo.getId(), photoInfo.getPhotoPath());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

               Glide.with(MedetailsActivity.this).load(photoInfo.getPhotoPath()).into(head);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(MedetailsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };


}
