package com.example.shopping;

import android.util.Log;

import com.example.httplibrary.callback.BaseCallback;
import com.example.shopping.wanandroid.Response;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public abstract class HttpCallBack<T> extends BaseCallback<T> {
    Response response;

    @Override
    protected T onConvert(String result) {
        T t = null;
        response = new Gson().fromJson(result, Response.class);
        JsonElement data = response.getData();
        int errorCode = response.getErrorCode();
        String errorMsg = response.getErrorMsg();
        switch (errorCode) {
            case -1:
                onError(errorMsg, errorCode);
                break;
            default:
                if (isCodeSuccess()) {
                    t = covert(data);
                }
                break;
        }
        Log.i("tag", t.toString());
        return t;
    }

    @Override
    public boolean isCodeSuccess() {
        if (response != null) {
            return response.getErrorCode() == 0;
        }
        return false;
    }
}
