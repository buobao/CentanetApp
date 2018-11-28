package com.cetnaline.findproperty.ui.fragments.personalcenter;

import android.util.Log;
import android.widget.TextView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseFragment;
import com.cetnaline.findproperty.base.IPresenter;

import butterknife.BindView;

public class PersonalCenterFragment extends BaseFragment {
    @BindView(R.id.test)
    protected TextView test;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void lazyLoad() {
        test.setText("lazyLoad");
        Log.i("buobao","才开始执行的");
    }
}
