package com.ljq.sushi.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/11/8.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fList;

    public FragmentAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fList) {
        super(fm);
        this.fList = fList;
    }

    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }


}
