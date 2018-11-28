package com.cetnaline.findproperty.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cetnaline.findproperty.bus.events.NormalEvent;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements BaseView {
    protected T mPresenter;
    protected View rootView;
    protected BaseActivity mActivity;

    private boolean isLoaded; //是否已经加载
    private boolean isCreateView; //是否已创建view

    public boolean isVisible;  //是否可见

    //布局
    protected abstract int getLayoutId();
    protected abstract T createPresenter();
    protected abstract void init();
    //需要延迟到可见时加载的内容 如：图片 数据库 网络等
    protected abstract void lazyLoad();
    //重新显示时的刷新和重加载操作
    protected void refreshLoad(){}
    protected void onInvisible(){
        isVisible = false;
    }
    protected void onVisible(){
        isVisible = true;
        if(isLoaded){
            refreshLoad();
        }
        if (!isLoaded && isCreateView && getUserVisibleHint()) {
            isLoaded = true;
            lazyLoad();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(getLayoutId(), container, false);
            isCreateView = true;
            if (mPresenter != null) {
                mPresenter.attachView(this);
                mPresenter.eventhandler();
            }
            ButterKnife.bind(this, rootView);
            init();
            onVisible();
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    @Override
    public void showMessage(String msg) {
        if (mActivity != null) {
            mActivity.showMessage(msg);
        }
    }

    @Override
    public void showMessage(int msgId) {
        if (mActivity != null) {
            mActivity.showMessage(msgId);
        }
    }

    @Override
    public void showLoadingDialog(boolean cancelable) {
        if (mActivity != null) {
            mActivity.showLoadingDialog(cancelable);
        }
    }

    @Override
    public void cancelLoadingDialog() {
        if (mActivity != null) {
            mActivity.cancelLoadingDialog();
        }
    }

    @Override
    public void connectNetworkHandler() {

    }

    @Override
    public void disConnectNetworkHandler() {

    }

    @Override
    public void eventHandler(NormalEvent normalEvent) {

    }
}



























