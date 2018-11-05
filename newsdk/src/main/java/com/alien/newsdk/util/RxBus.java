package com.alien.newsdk.util;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by Administrator on 2016/7/1.
 *
 */
public class RxBus {

    private static final String TAG = "RxBus";

    private PublishProcessor<Object> mBus;
    private final Map<Class<?>,Object> mStickEventMap;

    private RxBus() {
        mBus = PublishProcessor.create();
        mStickEventMap = new ConcurrentHashMap<>();
    }


    public static RxBus get() {
        return InstanceHolder.instance;
    }

    public <T> Flowable<T> tObservable(final Class<T> tClass){
        return mBus.ofType(tClass);

    }

    public void post(@NonNull Object content) {
        synchronized (mStickEventMap){//线程锁
            mStickEventMap.put(content.getClass(),content);//将事件类型保存到Map集合
        }

        mBus.onNext(content);
    }

    /*订阅Sticky事件*/
    public <T> Flowable<T> tObservableStick(final Class<T> eventType){
        synchronized (mStickEventMap){
            final Flowable<T> observable=mBus.ofType(eventType);//获取发送事件的Observable
            final Object event=mStickEventMap.get(eventType);//根据事件类型作为key查找value对应的value
            if(null!=event){
                return  observable.mergeWith(Flowable.create(new FlowableOnSubscribe<T>() {
                    @Override
                    public void subscribe(FlowableEmitter<T> e) throws Exception {
                        e.onNext(eventType.cast(event));
                    }
                }, BackpressureStrategy.DROP));
            }else {
                return observable;//如果没有sticky 就返回observable
            }
        }
    }

//    Observable.create(new Observable.OnSubscribe<T>(){//通过mergeWith合并两个Observable
//        @Override
//        public void call(Subscriber<? super T> subscriber) {
//            //订阅 eventType.cast(event)直接将eventType转换为 T发送
//            subscriber.onNext(eventType.cast(event));
//        }
//    })

    /*根据eventType获取事件*/
    public <T> T getStickEvent(Class<T> eventType){
        synchronized (mStickEventMap){
            return  eventType.cast(mStickEventMap.get(eventType));
        }
    }
    /*移除指定类型的eventType的Sticky事件*/
    public <T> T removeStickyEvent(Class<T> eventType){
        synchronized (mStickEventMap){
            return eventType.cast(mStickEventMap.remove(eventType));
        }
    }
    /*移除所有的Sticky事件*/
    public void removeAllStickyEvents(){
        synchronized (mStickEventMap){
            mStickEventMap.clear();
        }
    }


    public void unsubscribeAll(){
        mBus.onComplete();
        removeAllStickyEvents();
        mBus = PublishProcessor.create();
    }


    static class InstanceHolder{
        static RxBus instance = new RxBus();
    }


}
