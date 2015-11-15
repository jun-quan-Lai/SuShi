package com.ljq.sushi.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ljq.sushi.Fragment.FragmentAdapter;
import com.ljq.sushi.Fragment.LianHuaBaiKeFrag;
import com.ljq.sushi.Fragment.LianHuaChiFrag;
import com.ljq.sushi.Fragment.MyFrag;
import com.ljq.sushi.R;
import com.ljq.sushi.Fragment.SameCityFrag;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ViewPager vPager;
    private List<Fragment> fList; //Fragment的容器
    private RadioGroup radioGroup;
    private RadioButton lianHuaChiRBtn;//主页底部4个按钮
    private RadioButton sameCityRBtn;
    private RadioButton baiKeRBtn;
    private RadioButton meRBtn;
    final int LIAN_HUA_CHI = 0; //常量代表4个fragment页面
    final int SAME_CITY = 1;
    final int LIAN_HUA_BAI_KE = 2;
    final int MY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        setViewPager();
        addListenerForvPager();
        setListenerForRadioGroup();
    }

    //配置ViewPager,实现滑动功能
    private void setViewPager(){
        getSupportActionBar().hide();//隐藏标题栏

        vPager = (ViewPager)findViewById(R.id.vp);
        fList = new ArrayList<Fragment>();
        fList.add(new LianHuaChiFrag());
        fList.add(new SameCityFrag());
        fList.add(new LianHuaBaiKeFrag());
        fList.add(new MyFrag());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fList);
        vPager.setAdapter(adapter);
    }

    private void addListenerForvPager(){
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetBtn();
                switch (position){
                    case LIAN_HUA_CHI:
                        lianHuaChiRBtn.setCompoundDrawablesWithIntrinsicBounds
                                (null,getResources().getDrawable(R.mipmap.tab_lhc_pressed),null,null);
                        break;
                    case SAME_CITY:
                        sameCityRBtn.setCompoundDrawablesWithIntrinsicBounds
                                (null, getResources().getDrawable(R.mipmap.tab_city_pressed), null, null);
                        break;
                    case LIAN_HUA_BAI_KE:
                        baiKeRBtn.setCompoundDrawablesWithIntrinsicBounds
                                (null, getResources().getDrawable(R.mipmap.tab_baike_pressed), null, null);
                        break;
                    case MY:
                        meRBtn.setCompoundDrawablesWithIntrinsicBounds
                                (null, getResources().getDrawable(R.mipmap.tab_me_pressed), null, null);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //为主页4个按钮设置监听器，实现按按钮切换fragment的功能
    private void setListenerForRadioGroup(){
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        lianHuaChiRBtn = (RadioButton)findViewById(R.id.lian_hua_chi);
        sameCityRBtn = (RadioButton)findViewById(R.id.same_city);
        baiKeRBtn = (RadioButton)findViewById(R.id.lian_hua_bai_ke);
        meRBtn = (RadioButton)findViewById(R.id.my);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetBtn();
                //此处使用switch语句时出现问题，有空再解决
                if (lianHuaChiRBtn.getId() == checkedId) {
                    vPager.setCurrentItem(LIAN_HUA_CHI);
                    //使用过时的getDrawable原因：新的函数要求API>=21，本项目最低API为11
                    lianHuaChiRBtn.setCompoundDrawablesWithIntrinsicBounds
                            (null,getResources().getDrawable(R.mipmap.tab_lhc_pressed),null,null);
                } else if (sameCityRBtn.getId() == checkedId) {
                    vPager.setCurrentItem(SAME_CITY);
                    sameCityRBtn.setCompoundDrawablesWithIntrinsicBounds
                            (null, getResources().getDrawable(R.mipmap.tab_city_pressed), null, null);
                } else if (baiKeRBtn.getId() == checkedId) {
                    vPager.setCurrentItem(LIAN_HUA_BAI_KE);
                    baiKeRBtn.setCompoundDrawablesWithIntrinsicBounds
                            (null, getResources().getDrawable(R.mipmap.tab_baike_pressed), null, null);
                } else if (meRBtn.getId() == checkedId) {
                    vPager.setCurrentItem(MY);
                    meRBtn.setCompoundDrawablesWithIntrinsicBounds
                            (null, getResources().getDrawable(R.mipmap.tab_me_pressed), null, null);
                }
            }
        });
    }

    //设置button的图片为normal状态
    private void resetBtn(){
        lianHuaChiRBtn.setCompoundDrawablesWithIntrinsicBounds
                (null, getResources().getDrawable(R.mipmap.tab_lhc_normal), null, null);
        sameCityRBtn.setCompoundDrawablesWithIntrinsicBounds
                (null, getResources().getDrawable(R.mipmap.tab_city_normal), null, null);
        baiKeRBtn.setCompoundDrawablesWithIntrinsicBounds
                (null, getResources().getDrawable(R.mipmap.tab_baike_normal), null, null);
        meRBtn.setCompoundDrawablesWithIntrinsicBounds
                (null, getResources().getDrawable(R.mipmap.tab_me_normal), null, null);

    }

}
