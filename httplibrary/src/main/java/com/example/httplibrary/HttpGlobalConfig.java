package com.example.httplibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

public class HttpGlobalConfig {
    //baseUrl
    private String baseUrl;
    private long timeout;
    private TimeUnit timeUnit;
    //公共的参数
    private Map<String, Object> baseparams;
    //公共的头信息
    private Map<String, Object> baseHeaders;
    //公共的拦截器
    private ArrayList<Interceptor> interceptors;

    //日志开关
    private boolean isShowLog;

    private Context context;

    private Handler handler;

    private HttpGlobalConfig() {
    }

    //静态内部类
    private static final class HttpClobalConfigHolder {
        private static HttpGlobalConfig INSTANCE = new HttpGlobalConfig();
    }

    public static HttpGlobalConfig getInsance() {
        return HttpClobalConfigHolder.INSTANCE;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpGlobalConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return HttpGlobalConfig.getInsance();
    }

    public long getTimeout() {
        return timeout;
    }

    public HttpGlobalConfig setTimeout(long timeout) {
        this.timeout = timeout;
        return HttpGlobalConfig.getInsance();
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public HttpGlobalConfig setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return HttpGlobalConfig.getInsance();
    }

    public Map<String, Object> getBaseparams() {
        return baseparams;
    }

    public HttpGlobalConfig setBaseparams(Map<String, Object> baseparams) {
        this.baseparams = baseparams;
        return HttpGlobalConfig.getInsance();
    }

    public Map<String, Object> getBaseHeaders() {
        return baseHeaders;
    }

    public HttpGlobalConfig setBaseHeaders(Map<String, Object> baseHeaders) {
        this.baseHeaders = baseHeaders;
        return HttpGlobalConfig.getInsance();
    }

    public ArrayList<Interceptor> getInterceptors() {
        return interceptors;
    }

    public HttpGlobalConfig setInterceptors(ArrayList<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return HttpGlobalConfig.getInsance();
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public HttpGlobalConfig setShowLog(boolean showLog) {
        isShowLog = showLog;
        return HttpGlobalConfig.getInsance();
    }

    public HttpGlobalConfig initReaddy(Context context) {
        this.context = context.getApplicationContext();
        handler = new Handler(Looper.getMainLooper());
        return HttpGlobalConfig.getInsance();
    }

    public Handler getHandler() {
        return handler;
    }
}
