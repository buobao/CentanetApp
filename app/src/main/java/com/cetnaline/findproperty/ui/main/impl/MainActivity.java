package com.cetnaline.findproperty.ui.main.impl;
import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.ui.fragments.active.ActiveFragment;
import com.cetnaline.findproperty.ui.fragments.chatconversation.ChatConversationFragment;
import com.cetnaline.findproperty.ui.fragments.home.impl.HomeFragment;
import com.cetnaline.findproperty.ui.fragments.personalcenter.PersonalCenterFragment;
import com.cetnaline.findproperty.ui.login.impl.LoginActivity;
import com.cetnaline.findproperty.ui.main.MainPresenter;
import com.cetnaline.findproperty.ui.main.MainView;
import com.cetnaline.findproperty.widgets.DisScrollViewPager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {
    @BindView(R.id.fragment_view_pager)
    protected DisScrollViewPager fragmentsPager;
    @BindView(R.id.bottom_tab_bar)
    protected CommonTabLayout bottomTabBar;
    @BindView(R.id.map_btn)
    protected ImageView mapButton;

    private int currentPage;

    @Override
    protected boolean fullScreenEnable() {
        return true;
    }

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
            if (!CacheHolder.getInstance().isLogin()) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                showMessage("当前用户已登录");
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener(){
                @Override
                public void onGranted() {
                    showMessage("已授权定位");
                }

                @Override
                public void onDenied(List<String> grantedPermission, List<String> deniedPermission) {
                    showMessage("已拒绝定位");
                }
            });
        }

        if (CacheHolder.getInstance().isLogin()) {
            mPresenter.connectRongCloudServer();
        }

    }

    @Override
    public void eventHandler(NormalEvent normalEvent) {
        //登录成功连接融云
        if (normalEvent.getCode() == NormalEvent.LOGIN_SUCCESS) {
            mPresenter.connectRongCloudServer();
        }
    }


}



























