package com.ljq.sushi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljq.sushi.Adapter.BaikeFragAdapter;
import com.ljq.sushi.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/8.
 */
public class BaiKeFrag extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Fragment> fragmentList; //页面列表
    private FragmentManager mFragmanager;

    public static BaiKeFrag newInstance(){
        BaiKeFrag fragment = new BaiKeFrag();
        return fragment;
    }

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
        mFragmanager=getChildFragmentManager(); //这里是嵌套的Fragment，一定要使用getChildFragmentManager
        initView();
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

    private void setViewPager() {
        viewPager = (ViewPager) getView().findViewById(R.id.baike_vp);
        fragmentList = new ArrayList<>();
        fragmentList.add(BaiKe_tab1_Frag.newInstance());
        fragmentList.add(BaiKe_tab2_Frag.newInstance());
        fragmentList.add(BaiKe_tab3_Frag.newInstance());
        fragmentList.add(BaiKe_tab4_Frag.newInstance());
        fragmentList.add(BaiKe_tab5_Frag.newInstance());
        fragmentList.add(BaiKe_tab6_Frag.newInstance());
        viewPager.setAdapter(new BaikeFragAdapter(mFragmanager,fragmentList));
    }

    private void initView() {
        setViewPager();
        tabLayout = (TabLayout) getView().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
