package com.cetnaline.findproperty;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.base.IPresenter;
import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.TextEvent;

public class TwoActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_two;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        RxBus.getInstance().post(new TextEvent());
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }
}
