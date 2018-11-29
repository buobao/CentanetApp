package com.cetnaline.findproperty.ui.map.impl;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.ui.map.MapPresenter;
import com.cetnaline.findproperty.ui.map.MapView;
import com.cetnaline.findproperty.utils.LocationUtil;

import java.util.List;

import butterknife.BindView;

public class MapActivity extends BaseActivity<MapPresenter> implements MapView {
    @BindView(R.id.map)
    protected com.amap.api.maps.MapView aMapView;

    private AMap aMap;
    private AMapLocationClient aMapLocationClient;

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        aMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = aMapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(LocationUtil.SH_LAT, LocationUtil.SH_LNG)));

            UiSettings uiSettings = aMap.getUiSettings();
//            uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
            uiSettings.setZoomControlsEnabled(false);
            uiSettings.setMyLocationButtonEnabled(false); //地图定位标志隐藏
            aMap.setMyLocationEnabled(true);

            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
//            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);
        }

//        aMapLocationClient = LocationUtil.getOnceInstance(this, aMapLocation -> {
//            //定位处理
//
//            aMapLocationClient.stopLocation();
//        });
//
//        requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
//            @Override
//            public void onGranted() {
//                aMapLocationClient.startLocation();
//            }
//
//            @Override
//            public void onDenied(List<String> grantedPermission, List<String> deniedPermission) {
//                showMessage(getResources().getString(R.string.no_location_permission_msg));
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        aMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        aMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        aMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapView.onDestroy();
        if (aMapLocationClient != null) {
            aMapLocationClient.onDestroy();
        }
    }

    @Override
    protected MapPresenter createPresenter() {
        return null;
    }
}
