package com.cetnaline.findproperty.ui.fragments.home.impl;

import android.view.View;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseFragment;
import com.cetnaline.findproperty.ui.fragments.home.HomePresenter;
import com.cetnaline.findproperty.ui.fragments.home.HomeView;
import com.cetnaline.findproperty.widgets.CustomListView;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {
    @BindView(R.id.list)
    CustomListView listView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenterImpl();
    }

    @Override
    protected void init() {
        View header = View.inflate(getActivity(), R.layout.header_home, null);
        listView.setAdapter(new HomeHouseListAdapter(android.R.layout.simple_list_item_2,new ArrayList()));
        listView.addHeadView(header);
    }

    @Override
    protected void lazyLoad() {

    }
}
