package com.ljq.sushi.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ljq.sushi.R;

public class MedetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button changeImgbtn;
    private LinearLayout introduction;
    private  LinearLayout name;
    private LinearLayout sex;
    private LinearLayout phoneNo;
    private LinearLayout email;
    private LinearLayout motto;

    private TextView txt_introduction;
    private TextView txt_name;
    private TextView txt_sex;
    private TextView txt_phoneNo;
    private TextView txt_email;
    private TextView txt_motto;

     //private Spinner sexSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medetails);

        initView();
    }

    private void initView() {
        changeImgbtn = (Button) findViewById(R.id.userInfo_changeHeadimg_btn);
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

        changeImgbtn.setOnClickListener(this);
        introduction.setOnClickListener(this);
        name.setOnClickListener(this);
        sex.setOnClickListener(this);
        phoneNo.setOnClickListener(this);
        email.setOnClickListener(this);
        motto.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        final EditText ed;
        switch (v.getId()){
            case R.id.userInfo_changeHeadimg_btn:

                break;
            case R.id.userInfo_introduction:
                ed = new EditText(this);
                ed.setText(txt_introduction.getText().toString());
                new AlertDialog.Builder(this).setTitle("简介").setView(
                        ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_introduction.setText(ed.getText().toString());
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
                        txt_name.setText(ed.getText().toString());
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
                        txt_sex.setText(sexSpinner.getSelectedItemPosition()==1 ? "男":"女");
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
                        txt_phoneNo.setText(ed.getText().toString());
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
                        txt_email.setText(ed.getText().toString());
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
                        txt_motto.setText(ed.getText().toString());
                    }
                }).setNegativeButton("取消", null).show();
                break;
            default:
                break;
        }
    }
}
