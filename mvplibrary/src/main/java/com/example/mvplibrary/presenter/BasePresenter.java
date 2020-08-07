package com.example.mvplibrary.presenter;

import com.example.mvplibrary.view.BaseView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends BaseView> {
    public V mView;

    private WeakReference<V> weakReference;

    //绑定view
    public void attachView(V v) {
        weakReference = new WeakReference<V>(v);
        mView = weakReference.get();
    }

    //返回视图层对象
    public LifecycleProvider getLiftCycle() {
        return (LifecycleProvider) mView;
    }

    //解绑view
    public void destroyView() {
        if (weakReference != null) {
            weakReference.clear();
        }
    }
}
