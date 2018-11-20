package com.cetnaline.findproperty.base;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends IView> implements IPresenter<T> {
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
}
