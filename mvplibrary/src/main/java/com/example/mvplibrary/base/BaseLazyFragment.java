package com.example.mvplibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvplibrary.presenter.BasePresenter;
import com.example.mvplibrary.view.BaseView;

public abstract class BaseLazyFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment {
    boolean mIsPrepare = false;//视图是否初始化完成

    boolean mIsVisible = false;//是否可见

    boolean mIsFirstLoad = false;//是否是第一次加载

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mIsPrepare = true;
        lazyLoad();//懒加载
        return view;

    }

    private void lazyLoad() {
        //判断条件  1.界面以创建完 2.该界面用户可见 3.第一次加载数据
        if (mIsPrepare && mIsVisible && mIsFirstLoad) {
            //加载数据
            layinitData();
        }
    }

    protected abstract void layinitData();


    //懒加载

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepare = false;
        mIsVisible = false;
        mIsFirstLoad = true;
    }
}
