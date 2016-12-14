package com.lhr.jiandou.utils.RxBus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by ChinaLHR on 2016/12/14.
 * Email:13435500980@163.com
 */

public class RxBus {
    //静态内部类单例实现
    private static class Holder {
        private static final RxBus INSTANCE = new RxBus();
    }

    private RxBus() {

    }

    public static final RxBus getInstance() {
        return Holder.INSTANCE;
    }
    //PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private final rx.subjects.Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    // 发送一个新的事件
    public void post(Object o){
        bus.onNext(o);
    }

    //根据传递的 Type 类型返回特定类型(Type)的 被观察者
    public <T>Observable<T> toObservable(Class<T> type){
        //ofType发送指定类型
        return bus.ofType(type);
    }




}
