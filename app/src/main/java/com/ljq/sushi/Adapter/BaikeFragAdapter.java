package com.ljq.sushi.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/11/8.
 */
public class BaikeFragAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =6;
    private String tabTitles[] = new String[]{"推荐","素食","菜谱","健康","环保","国学"};
    private List<Fragment> fList;
    public BaikeFragAdapter(FragmentManager fm, List<Fragment> fList) {
        super(fm);
        this.fList = fList;
    }

    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
