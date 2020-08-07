package com.example.httplibrary.callback;

import android.text.TextUtils;

import com.example.httplibrary.HttpGlobalConfig;
import com.example.httplibrary.disposable.RequestManagerImp;
import com.example.httplibrary.excepteion.ApiException;
import com.example.httplibrary.excepteion.ExceptionEngine;
import com.example.httplibrary.utils.LogUtils;
import com.example.httplibrary.utils.ThreadUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver implements Observer {
    String tag;
    public void setTag(String tag) {
        this.tag = tag;
    }
    @Override
    public void onSubscribe(Disposable d) {
        if (!TextUtils.isEmpty(tag)) {
            RequestManagerImp.getInstance().add(tag, d);
        }
    }

    @Override
    public void onNext(Object t) {
        LogUtils.e(t.toString());
        if (!TextUtils.isEmpty(tag)) {
            RequestManagerImp.getInstance().remove(tag);
        }
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            onError(apiException.getMsg(), apiException.getCode());
        } else {
            onError("未知异常", ExceptionEngine.UN_KNOWN_ERROR);
        }
        if (!TextUtils.isEmpty(tag)) {
            RequestManagerImp.getInstance().remove(tag);
        }
    }

    @Override
    public void onComplete() {
        if (!RequestManagerImp.getInstance().isDispose(tag)) {
            RequestManagerImp.getInstance().cancle(tag);
        }
    }

    //回调错误信息
    public abstract void onError(String msg, int code);

    public abstract void cancle();

    //网络请求取消
    public void cancled() {
        if (!ThreadUtils.isMainThread()) {
            HttpGlobalConfig.getInsance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    cancled();
                }
            });
        }
    }
}
