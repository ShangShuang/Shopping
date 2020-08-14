package com.example.httplibrary.callback;

import android.util.Log;

import com.example.httplibrary.excepteion.ExceptionEngine;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public abstract class BaseCallback<T> extends BaseObserver {
    //解析成功的标志
    boolean callSuccess = true;

    @Override
    public void onNext(Object o) {
        super.onNext(o);
        Log.e("111", "onNext: " + o.toString());
        //返回的是个json串
        T parse = parse((String) o);
        Log.e("111", "onNext: " + parse.toString());
        if (callSuccess && isCodeSuccess()) {
            onSuccess(parse);
        }
    }

    public T parse(String result) {
        T data = null;
        try {
            data = onConvert(result);
            callSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            callSuccess = false;
            onError("解析错误", ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR);
        }

        return data;
    }

    //将jsonElement转换为Response,并且通过子类的实现获得data数据
    protected abstract T onConvert(String result);


    //数据返回状态成功
    public abstract boolean isCodeSuccess();

    //返回获得的泛型数据
    public abstract void onSuccess(T t);

    //将我们需要的数据解析出零四
    public abstract T covert(JsonElement result);


}
