package com.ljq.sushi.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ljq.sushi.Fragment.FragmentAdapter;
import com.ljq.sushi.Fragment.LianHuaBaiKeFrag;
import com.ljq.sushi.Fragment.LianHuaChiFrag;
import com.ljq.sushi.Fragment.MyFrag;
import com.ljq.sushi.Fragment.SameCityFrag;
import com.ljq.sushi.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 实现主界面框架
 */
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_frame);
        initView();
        setViewPager();

        addListenerForVPager();
        setListenerForRadioGroup();
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        lianHuaChiRBtn = (RadioButton) findViewById(R.id.lian_hua_chi);
        sameCityRBtn = (RadioButton) findViewById(R.id.same_city);
        baiKeRBtn = (RadioButton) findViewById(R.id.lian_hua_bai_ke);
        meRBtn = (RadioButton) findViewById(R.id.my);
        vPager = (ViewPager) findViewById(R.id.vp);
    }

    //配置ViewPager,实现滑动功能
    private void setViewPager() {
        fList = new ArrayList<>();
        fList.add(new LianHuaChiFrag());
        fList.add(new SameCityFrag());
        fList.add(new LianHuaBaiKeFrag());
        fList.add(new MyFrag());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fList);
        vPager.setAdapter(adapter);
    }

    private void addListenerForVPager() {
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case LIAN_HUA_CHI:
                        lianHuaChiRBtn.setChecked(true);
                        break;
                    case SAME_CITY:
                        sameCityRBtn.setChecked(true);
                        break;
                    case LIAN_HUA_BAI_KE:
                        baiKeRBtn.setChecked(true);
                        break;
                    case MY:
                        meRBtn.setChecked(true);
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
    private void setListenerForRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.lian_hua_chi:
                        vPager.setCurrentItem(LIAN_HUA_CHI);
                        break;
                    case R.id.same_city:
                        vPager.setCurrentItem(SAME_CITY);
                        break;
                    case R.id.lian_hua_bai_ke:
                        vPager.setCurrentItem(LIAN_HUA_BAI_KE);
                        break;
                    case R.id.my:
                        vPager.setCurrentItem(MY);
                        break;
                    default:
                        break;

                }
            }
        });
    }

}
