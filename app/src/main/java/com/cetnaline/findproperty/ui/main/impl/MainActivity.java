package com.cetnaline.findproperty.ui.main.impl;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.ui.fragments.active.ActiveFragment;
import com.cetnaline.findproperty.ui.fragments.chatconversation.ChatConversationFragment;
import com.cetnaline.findproperty.ui.fragments.home.HomeFragment;
import com.cetnaline.findproperty.ui.fragments.home.MainPagerAdapter;
import com.cetnaline.findproperty.ui.fragments.personalcenter.PersonalCenterFragment;
import com.cetnaline.findproperty.ui.main.MainPresenter;
import com.cetnaline.findproperty.ui.main.MainView;
import com.cetnaline.findproperty.ui.map.impl.MapActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {
    @BindView(R.id.fragment_view_pager)
    protected ViewPager fragmentsPager;
    @BindView(R.id.bottom_tab_bar)
    protected CommonTabLayout bottomTabBar;
    @BindView(R.id.map_btn)
    protected ImageView mapButton;

    private int currentPage;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        currentPage = 0;
        fragmentsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < 2) {
                    bottomTabBar.setCurrentTab(position);
                } else {
                    bottomTabBar.setCurrentTab(position+1);
                }
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        fragmentsPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),this,new ArrayList(){{
            add(new HomeFragment());
            add(new ActiveFragment());
            add(new ChatConversationFragment());
            add(new PersonalCenterFragment());
        }}, true));
        fragmentsPager.setCurrentItem(currentPage, false);

        bottomTabBar.setTabData(new ArrayList() {
            {
                add(new TabEntity("首页", R.drawable.ic_main_home_selected, R.drawable.ic_main_home_normal));
                add(new TabEntity("发现", R.drawable.ic_main_found_selected, R.drawable.ic_main_found_normal));
                add(new TabEntity("", R.drawable.ic_placeholder, R.drawable.ic_placeholder));
                add(new TabEntity("咨询", R.drawable.ic_main_msg_selected, R.drawable.ic_main_msg_normal));
                add(new TabEntity("我的", R.drawable.ic_main_mine_selected, R.drawable.ic_main_mine_normal));
            }
        });

        bottomTabBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position != 2) {
                    if (position < 2) {
                        currentPage = position;
                    } else {
                        currentPage = position - 1;
                    }
                    fragmentsPager.setCurrentItem(currentPage, false);
                } else {
                    bottomTabBar.setCurrentTab(currentPage);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mPresenter.onViewClick(mapButton, v -> {
            //打开地图找房
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });
    }

}



























