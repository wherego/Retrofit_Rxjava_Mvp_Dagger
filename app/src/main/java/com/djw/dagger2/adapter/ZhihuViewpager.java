package com.djw.dagger2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by JasonDong on 2017/3/23.
 */

public class ZhihuViewpager extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    private String[] titles = {"日报", "主题", "专栏", "热门"};

    public ZhihuViewpager(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
