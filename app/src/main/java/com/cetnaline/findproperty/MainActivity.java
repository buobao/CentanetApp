package com.cetnaline.findproperty;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.BaseEvent;
import com.cetnaline.findproperty.bus.events.TextEvent;
import com.cetnaline.findproperty.utils.RxUtil;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {
    @BindView(R.id.textView)
    protected TextView textView;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                toast("全部已授权");
            }

            @Override
            public void onGranted(List<String> grantedPermission) {
                for (String s : grantedPermission) {
                    switch (s) {
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            toast("存储已授权");
                            break;
                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            toast("定位已授权");
                            break;
                    }
                }
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String s : deniedPermission) {
                    switch (s) {
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            toast("存储未授权");
                            break;
                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            toast("定位未授权");
                            break;
                    }
                }
            }
        });

        textView.setText("MainTab");
        textView.setOnClickListener(v->{
            Intent intent = new Intent(this, TwoActivity.class);
            startActivity(intent);
        });

        RxBus.getInstance().toFlowable(BaseEvent.class).compose(RxUtil.flowableApplyToScheduler()).subscribe(baseEvent -> {
            textView.setText("refresh by two activity.");
            if (baseEvent instanceof TextEvent) {
                textView.setText("TextEvent");
            }
        });

    }
}
