package com.example.shopping.mvp;

import com.example.mvplibrary.model.ModelFactory;
import com.example.mvplibrary.presenter.BasePresenter;
import com.example.shopping.wanandroid.Demo;

public class MyPresenter extends BasePresenter<MyView> implements MyModel.callBack1 {
    //获取数据
    public void getData() {
        MyModel model = ModelFactory.createModel(MyModel.class);
        //model中的数据
        model.getData(this);
    }

    //实现接口
    @Override
    public void showDemo(Demo demo) {
        mView.showDemo(demo);
    }
}
