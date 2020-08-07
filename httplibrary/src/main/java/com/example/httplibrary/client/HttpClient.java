package com.example.httplibrary.client;

import android.text.TextUtils;
import android.util.Log;

import com.example.httplibrary.HttpConstant;
import com.example.httplibrary.HttpGlobalConfig;
import com.example.httplibrary.HttpsManager;
import com.example.httplibrary.callback.BaseCallback;
import com.example.httplibrary.service.ApiService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class HttpClient {
    //请求方式
    Method method;
    //请求参数
    Map<String, Object> params;
    //请求头
    Map<String, Object> headers;
    //rxjava绑定生命周期
    LifecycleProvider lifecycleProvider;

    //activity绑定生命周期
    ActivityEvent activityEvent;
    //fragment绑定生命周期

    FragmentEvent fragmentEvent;

    String baseUrl;
    //拼接url
    String apiUrl;
    //是否是json串
    boolean isJson;
    //json字符串
    String jsonBody;
    //超时时间
    long time;
    //时间单位
    TimeUnit timeUnit;

    //回调接口
    BaseCallback baseCallback;
    //订阅关系
    String tag;

    public HttpClient(Builder builder) {
        this.method = builder.method;
        this.activityEvent = builder.activityEvent;
        this.apiUrl = builder.apiUrl;
        this.fragmentEvent = builder.fragmentEvent;
        this.headers = builder.headers;
        this.baseUrl = builder.baseUrl;
        this.isJson = builder.isJson;
        this.jsonBody = builder.jsonBody;
        this.params = builder.params;
        this.lifecycleProvider = builder.lifecycleProvider;
        this.time = builder.time;
        this.timeUnit = builder.timeUnit;
    }

    public void request(BaseCallback baseCallback) {
        if (baseCallback == null) {
            new RuntimeException("异常");
        }
        this.baseCallback = baseCallback;
        doRequest();
    }

    private void doRequest() {
        //组装Obserable,根据请求方式返回对应的Obserable，去处理一异常结果的回调
        if (TextUtils.isEmpty(tag)) {
            tag = System.currentTimeMillis() + "";
        }

        baseCallback.setTag(tag);

        //添加参数信息
        addParams();
        //添加头信息
        addHeaders();
        if (HttpGlobalConfig.getInsance().getBaseUrl() != null) {
            this.baseUrl = HttpGlobalConfig.getInsance().getBaseUrl();
        }
        Observable observable = createObservable();
        HttpObserable httpObserable = new HttpObserable.Builder(observable)
                .setActivityEvent(activityEvent)
                .setBaseObserver(baseCallback)
                .setFragmentEvent(fragmentEvent)
                .build();
        //订阅
        httpObserable.observer().subscribe(baseCallback);
    }

    private void addHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (HttpGlobalConfig.getInsance().getBaseHeaders() != null) {
            headers.putAll(HttpGlobalConfig.getInsance().getBaseHeaders());
        }
    }

    private void addParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        if (HttpGlobalConfig.getInsance().getBaseparams() != null) {
            params.putAll(HttpGlobalConfig.getInsance().getBaseparams());
        }
    }
    //创建Observable

    private Observable createObservable() {
        Observable observable = null;
        boolean hasBodyString = !TextUtils.isEmpty(jsonBody);
        RequestBody requestBody = null;
        if (hasBodyString) {
            String mediaType = isJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(MediaType.parse(mediaType), jsonBody);
        }

        //默认请求是POST
        if (method == null) {
            method = Method.POST;
        }
        if (HttpGlobalConfig.getInsance().getTimeout() != 0) {
            this.time = HttpGlobalConfig.getInsance().getTimeout();
        }
        if (this.time == 0) {
            this.time = HttpConstant.TIME_OUT;
        }
        if (HttpGlobalConfig.getInsance().getTimeUnit() != null) {
            this.timeUnit = HttpGlobalConfig.getInsance().getTimeUnit();
        }

        if (this.timeUnit == null) {
            this.timeUnit = HttpConstant.TIME_UNIT;
        }

        Log.e("TAG", "createObservable: " + baseUrl);

        Retrofit retrofit = HttpsManager.getInstance()
                .getRetrofit(baseUrl, time, timeUnit);
        ApiService apiService = retrofit.create(ApiService.class);
        switch (method) {
            case POST:
                if (isJson) {
                    observable = apiService.postJson(apiUrl, requestBody, headers);
                } else {
                    observable = apiService.post(apiUrl, params, headers);
                }
                break;
            case GET:
                observable = apiService.get(apiUrl, params, headers);
                break;
            case DELETE:
                observable = apiService.delete(apiUrl, params, headers);
                break;
            case PUT:
                observable = apiService.put(apiUrl, params, headers);
                break;
        }
        return observable;
    }

    public static final class Builder {
        //请求方式
        Method method;
        //请求参数
        Map<String, Object> params;
        //请求头
        Map<String, Object> headers;
        //rxjava绑定生命周期
        LifecycleProvider lifecycleProvider;
        //activity绑定生命周期
        ActivityEvent activityEvent;
        //fragment绑定生命周期
        FragmentEvent fragmentEvent;
        String baseUrl;
        //拼接url
        String apiUrl;
        //是否是json串
        boolean isJson;
        //json字符串
        String jsonBody;
        //超时时间
        long time;
        //时间单位
        TimeUnit timeUnit;

        //get请求
        public Builder get() {
            this.method = Method.GET;
            return this;
        }

        //post请求
        public Builder post() {
            this.method = Method.POST;
            return this;
        }

        //delete请求
        public Builder delete() {
            this.method = Method.DELETE;
            return this;
        }

        //put请求
        public Builder put() {
            this.method = Method.PUT;
            return this;
        }


        public Builder() {

        }

        //设置参数的拼接
        public Builder setParams(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        //请求头的拼接
        public Builder setHeaders(Map<String, Object> headers) {
            this.headers = headers;
            return this;
        }

        public LifecycleProvider getLifecycleProvider() {
            return lifecycleProvider;
        }

        //用于绑定rxjava生命周期
        public Builder setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }


        //用于绑定Activity生命周期
        public Builder setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public ActivityEvent getActivityEvent() {
            return activityEvent;
        }

        //用于绑定Fragment生命周期
        public Builder setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }

        public FragmentEvent getFragmentEvent() {
            return fragmentEvent;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public Builder setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public Builder setJsonBody(String jsonBody, boolean isJson) {
            this.jsonBody = jsonBody;
            this.isJson = isJson;
            return this;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public HttpClient build() {
            return new HttpClient(this);
        }
    }
}

