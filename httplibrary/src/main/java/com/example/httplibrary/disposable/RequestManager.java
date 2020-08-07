package com.example.httplibrary.disposable;

import io.reactivex.disposables.Disposable;

/*
 * 订阅关系关联的类*/
public interface RequestManager {
    //添加订阅关系
    void add(String tag, Disposable disposable);

    //移除订阅关系
    void remove(String tag);

    //取消订阅关系
    void cancle(String tag);

    //取消所有的订阅关系
    void cancleAll();
}
