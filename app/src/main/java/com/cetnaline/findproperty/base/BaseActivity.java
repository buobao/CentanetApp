package com.cetnaline.findproperty.base;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.utils.statusbar.StatusBarUtil;
import com.cetnaline.findproperty.widgets.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements BaseView {
    private PermissionListener mlistener;
    protected T mPresenter;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViewInit(savedInstanceState);
        if (getLayout() != 0) {
            setContentView(getLayout());
        }

        if (fullScreenEnable()) {
            //设置状态栏透明
            StatusBarUtil.setTranslucentStatus(this);
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                StatusBarUtil.setStatusBarColor(this,0x55000000);
            }
            if (paddingStatus()) {
                StatusBarUtil.setRootViewFitsSystemWindows(this,true);
            }
        }

        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        if (mPresenter!=null){
            mPresenter.attachView(this);
            mPresenter.eventHandler();
        }
        init(savedInstanceState);
    }

    protected void beforeViewInit(Bundle savedInstanceState){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    List<String> grantedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];

                        if (grantResult != PackageManager.PERMISSION_GRANTED) { //用户拒绝授权的权限
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        } else {  //用户同意的权限
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {
                        mlistener.onGranted();
                    } else {
                        mlistener.onDenied(grantedPermissions, deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
        mlistener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    protected abstract @LayoutRes int getLayout();
    protected abstract void init(@Nullable Bundle savedInstanceState);
    protected abstract T createPresenter();

    /**
     * 网路连接处理
     */
    @Override
    public void connectNetworkHandler() {

    }

    /**
     * 网络断开处理
     */
    @Override
    public void disConnectNetworkHandler() {

    }

    //事件处理
    @Override
    public void eventHandler(NormalEvent normalEvent) {

    }

    @Override
    public void finishView() {
        finish();
    }

    /**
     * 设置沉浸式状态栏
     * @return
     */
    protected boolean fullScreenEnable() {
        return false;
    }

    /**
     * 设置预留状态栏高度 默认不预留
     * @return
     */
    protected boolean paddingStatus() {
        return false;
    }

    protected void requestPermission(String[] permissions,@NonNull PermissionListener listener) {
        this.mlistener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);
        }else{
            mlistener.onGranted();
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int msgId) {
        showMessage(getResources().getString(msgId));
    }

    @Override
    public void showLoadingDialog(boolean cancelable) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this, cancelable);
        } else {
            loadingDialog.setCancelable(cancelable);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void cancelLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public interface PermissionListener {
        //授权成功
        void onGranted();
        //拒绝授权
        void onDenied(List<String> grantedPermission,List<String> deniedPermission);
    }

}
