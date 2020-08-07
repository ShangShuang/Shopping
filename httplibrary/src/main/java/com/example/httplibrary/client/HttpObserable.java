package com.example.httplibrary.client;

import com.example.httplibrary.callback.BaseObserver;
import com.example.httplibrary.excepteion.ExceptionEngine;
import com.example.httplibrary.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HttpObserable {
    LifecycleProvider lifecycleProvider;
    //绑定Activity具体的生命周的
    ActivityEvent activityEvent;
    //绑定Fragment的具体的生命周期的
    FragmentEvent fragmentEvent;
    Observable observable;
    BaseObserver baseObserver;

    public HttpObserable(Builder buidler) {
        this.lifecycleProvider = buidler.lifecycleProvider;
        this.activityEvent = buidler.activityEvent;
        this.fragmentEvent = buidler.fragmentEvent;
        this.observable = buidler.observable;
        this.baseObserver = buidler.baseObserver;
    }

    /**
     * 对初始化返回值JsonElement 进行转换操作。
     *
     * @return
     */
    public Observable map() {
        return observable.map(new Function<JsonElement, Object>() {
            @Override
            public Object apply(JsonElement o) {
                LogUtils.e(o.toString());
                return new Gson().toJson(o);
            }
        });
    }

    /*onErrorResumeNext*/
    //错误信息的分类回调
    private Observable onErrorResumeNext() {
        return bindlifecycle().onErrorResumeNext(new Function<Throwable, ObservableSource>() {
            @Override
            public ObservableSource apply(Throwable throwable) throws Exception {
                LogUtils.e(throwable.getMessage());
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });
    }

    //监听取消订阅的操作
    /*doOnDispose*/
    private Observable doOnDispose() {
        if (baseObserver != null) {
            return onErrorResumeNext().doOnDispose(new Action() {
                @Override
                public void run() throws Exception {
                    baseObserver.cancled();
                }
            });
        }
        return onErrorResumeNext();
    }

    //rxjava绑定生命周期
    private Observable bindlifecycle() {
        Observable observable = map();
        if (lifecycleProvider != null) {
            if (activityEvent != null || fragmentEvent != null) {
                //两个同时存在，以activity为准
                if (activityEvent != null && fragmentEvent != null) {
                    return map().compose(lifecycleProvider.bindUntilEvent(activityEvent));
                }
                if (activityEvent != null) {
                    return map().compose(lifecycleProvider.bindUntilEvent(activityEvent));
                }
                if (fragmentEvent != null) {
                    return map().compose(lifecycleProvider.bindUntilEvent(fragmentEvent));
                }
            } else {
                return map().compose(lifecycleProvider.bindToLifecycle());
            }
        }
        return observable;
    }

    //设置线程切换
    public Observable observer() {
        return doOnDispose().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final class Builder {
        LifecycleProvider lifecycleProvider;
        //绑定Activity具体的生命周的
        ActivityEvent activityEvent;
        //绑定Fragment的具体的生命周期的
        FragmentEvent fragmentEvent;
        Observable observable;
        BaseObserver baseObserver;

        public Builder setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }

        public Builder setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public Builder setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }

        public Builder setBaseObserver(BaseObserver baseObserver) {
            this.baseObserver = baseObserver;
            return this;
        }

        public Builder(Observable observable) {
            this.observable = observable;
        }

        public HttpObserable build() {
            return new HttpObserable(this);
        }
    }
}
