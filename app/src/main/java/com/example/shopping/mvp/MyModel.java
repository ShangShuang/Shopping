package com.example.shopping.mvp;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.httplibrary.utils.LogUtils;
import com.example.mvplibrary.model.BaseModel;
import com.example.shopping.HttpCallBack;
import com.example.shopping.wanandroid.Demo;
import com.google.gson.JsonElement;

public class MyModel implements BaseModel {
    interface callBack1 {
        void showDemo(Demo demo);
    }

    //
    public void getData(callBack1 callBack1) {
        new HttpClient.Builder()
                .setApiUrl("article/list/0/json")//拼接url
                .get()
                .build()
                .request(new HttpCallBack<Demo>() {

                    @Override
                    public void onError(String msg, int code) {
                        LogUtils.e(msg);
                    }

                    @Override
                    public void cancle() {

                    }

                    @Override
                    public void onSuccess(Demo demo) {
                        LogUtils.e(demo.toString());
                        callBack1.showDemo(demo);
                    }

                    @Override
                    public Demo covert(JsonElement result) {
                        return JsonUtils.jsonToClass(result, Demo.class);
                    }
                });
    }
}
