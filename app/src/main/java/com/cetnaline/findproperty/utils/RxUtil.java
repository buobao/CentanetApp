package com.cetnaline.findproperty.utils;

import android.view.View;

import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    public static <T> ObservableTransformer<T, T> applyIoSchedulers(){
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T, T> flowableApplyToScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Function<Throwable, ObservableSource<? extends BaseResponseBean<T>>> requestErrorHandler() {
        return throwable -> {
            BaseResponseBean<T> baseResponseBean = new BaseResponseBean();
            if (throwable instanceof ConnectException) {
                baseResponseBean.setResultNo(BaseResponseBean.REQUEST_NOT_CONNECTION_CODE);
                return Observable.just(baseResponseBean);
            }else if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
                baseResponseBean.setResultNo(BaseResponseBean.REQUEST_OVERTIME_CODE);
                return Observable.just(baseResponseBean);
            } else {
                baseResponseBean.setResultNo(BaseResponseBean.REQUEST_ERROR_CODE);
                return Observable.just(baseResponseBean);
            }
        };
    }

    public static <T> ObservableTransformer<BaseResponseBean<T>, BaseResponseBean<T>> handleNoErrorResult(){
        return upstream -> upstream.flatMap((Function<BaseResponseBean<T>, ObservableSource<BaseResponseBean<T>>>) tBaseResponseBean -> {
            return Observable.just(new BaseResponseBean<>());
        });
    }


    /**
     * 用于点击事件处理
     */
    public static class RxView implements ObservableOnSubscribe<View> {
        private ObservableEmitter<View> emitter;
        @Override
        public void subscribe(ObservableEmitter<View> emitter) {
            this.emitter = emitter;
        }

        public RxView(View view) {
            view.setOnClickListener(v -> emitter.onNext(view));
        }
    }
}






























