package com.cetnaline.findproperty.ui.fragments.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> list;
    private ArrayList<Fragment> fragments;
    private boolean isFragment;
    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context, ArrayList<String> list) {
        super(fm);
        this.list = list;
        this.context = context;
    }

    public MainPagerAdapter(FragmentManager fm, Context context, ArrayList<Fragment> fragments, boolean isFragment) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.isFragment = isFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (!isFragment) {
            return Fragment.instantiate(context, list.get(position));
        } else {
            return fragments.get(position);
        }
    }

    @Override
    public int getCount() {
        if (!isFragment) {
            return list.size();
        } else {
            return fragments.size();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
