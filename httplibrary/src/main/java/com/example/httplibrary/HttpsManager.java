package com.example.httplibrary;

import com.example.httplibrary.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpsManager {
    //单例
    private static volatile HttpsManager instance;

    public HttpsManager() {
    }

    public static HttpsManager getInstance() {
        if (instance == null) {
            synchronized (HttpsManager.class) {
                if (instance == null) {
                    instance = new HttpsManager();
                }
            }
        }
        return instance;
    }

    //封装retrofit
    public Retrofit getRetrofit(String baseUrl, long timeout, TimeUnit timeUnit) {
        return new Retrofit.Builder()
                .client(getBaseOkHttpsClient(timeout, timeUnit))
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //封装ok,拦截器
    private OkHttpClient getBaseOkHttpsClient(long timeout, TimeUnit timeUnit) {
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("okhttp====" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //自定义拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response;
            }
        };
        Interceptor[] interceptors = {httpLoggingInterceptor, interceptor};
        return getClienk(timeout, timeUnit, interceptors);
    }

    private OkHttpClient getClienk(long timeout, TimeUnit timeUnit, Interceptor[] interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(timeout, timeUnit);
        builder.writeTimeout(timeout, timeUnit);
        builder.readTimeout(timeout, timeUnit);
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        ArrayList<Interceptor> baseinterceptors = HttpGlobalConfig.getInsance().getInterceptors();
        if (baseinterceptors != null) {
            for (Interceptor baseinterceptor : baseinterceptors) {
                builder.addInterceptor(baseinterceptor);
            }
        }
        return builder.build();
    }
}
