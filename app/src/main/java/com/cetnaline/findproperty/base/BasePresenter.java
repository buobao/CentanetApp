package com.cetnaline.findproperty.base;

import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.cache.CacheHolder;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends BaseView> implements IPresenter<T> {
    public T iView;
    private Set<Disposable> disposables;

    @Override
    public void attachView(T view) {
        this.iView = view;
    }

    protected void addDisposable(Disposable disposable) {
        if (disposables == null) {
            disposables = new HashSet<>();
        }
        disposables.add(disposable);
    }

    @Override
    public void detachView() {
        this.iView = null;
        if (disposables!=null && disposables.size()>0) {
            for (Disposable disposable : disposables) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
    }

    @Override
    public void eventhandler() {
        addDisposable(RxBus.getInstance().toFlowable(NormalEvent.class)
                .subscribe(normalEvent -> {
                    if (normalEvent.getCode() == NormalEvent.NETWORK_CONNECTED) {
                        iView.connectNetworkHandler();
                    } else if (normalEvent.getCode() == NormalEvent.NETWORK_DISCONNECTED) {
                        iView.disConnectNetworkHandler();
                    } else {
                        iView.eventHandler(normalEvent);
                    }
                }));
    }
}
