package com.cetnaline.findproperty;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.base.IPresenter;

public class TwoActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_two;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }
}
