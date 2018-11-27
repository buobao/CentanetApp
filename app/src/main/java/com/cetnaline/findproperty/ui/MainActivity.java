package com.cetnaline.findproperty.ui;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.base.IPresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.textView)
    protected TextView textView;
    @BindView(R.id.imageView)
    protected ImageView imageView;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
//            @Override
//            public void onGranted() {
//                toast("全部已授权");
//            }
//
//            @Override
//            public void onGranted(List<String> grantedPermission) {
//                for (String s : grantedPermission) {
//                    switch (s) {
//                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
//                            toast("存储已授权");
//                            break;
//                        case Manifest.permission.ACCESS_FINE_LOCATION:
//                            toast("定位已授权");
//                            break;
//                    }
//                }
//            }
//
//            @Override
//            public void onDenied(List<String> deniedPermission) {
//                for (String s : deniedPermission) {
//                    switch (s) {
//                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
//                            toast("存储未授权");
//                            break;
//                        case Manifest.permission.ACCESS_FINE_LOCATION:
//                            toast("定位未授权");
//                            break;
//                    }
//                }
//            }
//        });

        textView.setText("MainTab");

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_load_err)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this)
                .asBitmap()
                .load("http://guolin.tech/test.gif")
                .apply(options)
                .into(imageView);

//        RxBus.getInstance().toFlowable(NormalEvent.class).compose(RxUtil.flowableApplyToScheduler()).subscribe(normalEvent -> {
//            textView.setText("refresh by two activity.");
//            if (normalEvent instanceof TextEvent) {
//                textView.setText("TextEvent");
//            }
//        });

    }

}







