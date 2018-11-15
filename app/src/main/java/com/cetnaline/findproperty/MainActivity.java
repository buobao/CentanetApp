package com.cetnaline.findproperty;

import android.Manifest;
import android.os.Bundle;
import com.cetnaline.findproperty.base.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
