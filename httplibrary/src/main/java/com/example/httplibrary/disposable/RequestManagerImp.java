package com.example.httplibrary.disposable;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;

/*
 * 订阅关系关联的实现类*/
public class RequestManagerImp implements RequestManager {
    private static volatile RequestManagerImp instance;
    //管理所有订阅关系的集合
    private Map<String, Disposable> maps = new HashMap<>();

    public RequestManagerImp() {

    }

    public static RequestManagerImp getInstance() {
        if (instance == null) {
            synchronized (RequestManagerImp.class) {
                if (instance == null) {
                    instance = new RequestManagerImp();
                }
            }
        }
        return instance;
    }

    //添加所有的订阅关系
    @Override
    public void add(String tag, Disposable disposable) {
        if (!TextUtils.isEmpty(tag)) {
            maps.put(tag, disposable);
        }
    }

    //移除所有的订阅关系
    @Override
    public void remove(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            if (maps.isEmpty()) {
                return;
            }
            maps.remove(tag);
        }
    }

    //取消订阅关系
    @Override
    public void cancle(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            if (!maps.isEmpty()) {
                if (maps.get(tag) != null) {
                    Disposable disposable = maps.get(tag);
                    if (disposable.isDisposed()) {
                        disposable.dispose();
                    }
                    maps.remove(tag);
                }
            }
        }
    }

    @Override
    public void cancleAll() {
        Disposable disposable = null;
        if (!maps.isEmpty()) {
            Set<String> keys = maps.keySet();
            for (String key : keys) {
                if (maps.get(key) != null) {
                    disposable = maps.get(key);
                }
                if (disposable != null && disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
        //清除所有的订阅关系
        maps.clear();
    }

    public boolean isDispose(String tag) {
        if (!maps.isEmpty() && maps.get(tag) != null) {
            return maps.get(tag).isDisposed();
        }
        return false;
    }
}
