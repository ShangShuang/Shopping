package com.example.mvplibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxAppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        unbinder = ButterKnife.bind(this);
        initView();
        initEvent();//初始化p
        initData();//获取数据


    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();

    protected abstract int initLayoutId();

    //ButterKnife解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
