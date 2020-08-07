package com.example.shopping;

import android.app.Application;

import com.example.httplibrary.HttpConstant;
import com.example.httplibrary.HttpGlobalConfig;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

public class ShopApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //腾讯bugly
        tengXunBug();
        //友盟
        youMeng();
        //内存泄漏工具
        leakCanary();

        HttpGlobalConfig.getInsance()
                .setBaseUrl("https://wanandroid.com/")
                .setTimeout(HttpConstant.TIME_OUT)
                .setTimeUnit(HttpConstant.TIME_UNIT)
                .setShowLog(true)
                .initReaddy(this);
    }

    private void leakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for
            // heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
    }

    private void youMeng() {
        UMConfigure.init(this, "5f2be63bd30932215475a596", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    private void tengXunBug() {
        //
        CrashReport.initCrashReport(getApplicationContext(), "989f874acd", false);

        //测试
//        CrashReport.testJavaCrash();
    }
}
