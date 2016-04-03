package com.ljq.sushi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ljq.sushi.Adapter.FragmentAdapter;
import com.ljq.sushi.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/8.
 */
public class BaiKeFrag extends Fragment {

    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList; //页面列表
    private FragmentManager mFragmanager;
    private RadioGroup topHost; //百科顶部导航

    private RadioButton tab1_btn;
    private RadioButton tab2_btn;
    private RadioButton tab3_btn;
    private RadioButton tab4_btn;
    private RadioButton tab5_btn;

    final int TAB1=0;
    final int TAB2=1;
    final int TAB3=2;
    final int TAB4=3;
    final int TAB5=4;

    public BaiKeFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_baike, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmanager=getChildFragmentManager();

        initView();
        setViewPager();
        addListenerForViewPager();
        addListenerForFragment();
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

    private void addListenerForFragment() {
        topHost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab1:
                        viewPager.setCurrentItem(TAB1);
                        break;
                    case R.id.tab2:
                        viewPager.setCurrentItem(TAB2);
                        break;
                    case R.id.tab3:
                        viewPager.setCurrentItem(TAB3);
                        break;
                    case R.id.tab4:
                        viewPager.setCurrentItem(TAB4);
                        break;
                    case R.id.tab5:
                        viewPager.setCurrentItem(TAB5);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void addListenerForViewPager() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case TAB1:
                        tab1_btn.setChecked(true);
                        break;
                    case TAB2:
                        tab2_btn.setChecked(true);
                        break;
                    case TAB3:
                        tab3_btn.setChecked(true);
                        break;
                    case TAB4:
                        tab4_btn.setChecked(true);
                        break;
                    case TAB5:
                        tab5_btn.setChecked(true);
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

    private void setViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new BaiKe_tab1_Frag());
        fragmentList.add(new BaiKe_tab2_Frag());
        fragmentList.add(new BaiKe_tab3_Frag());
        fragmentList.add(new BaiKe_tab4_Frag());
        fragmentList.add(new BaiKe_tab5_Frag());

        viewPager.setAdapter(new FragmentAdapter(mFragmanager,fragmentList));
    }

    private void initView() {
        viewPager = (ViewPager) getView().findViewById(R.id.baike_vp);
        topHost = (RadioGroup) getView().findViewById(R.id.baike_radioGroup);
        tab1_btn = (RadioButton) getView().findViewById(R.id.tab1);
        tab2_btn = (RadioButton) getView().findViewById(R.id.tab2);
        tab3_btn = (RadioButton) getView().findViewById(R.id.tab3);
        tab4_btn = (RadioButton) getView().findViewById(R.id.tab4);
        tab5_btn = (RadioButton) getView().findViewById(R.id.tab5);
    }
}
