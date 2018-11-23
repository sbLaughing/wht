package com.alien.newsdk.extensions

import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.MaybeSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * 描述:
 *
 * Created by and on 2018/11/22.
 */
fun <T> Maybe<T>.autoDispose(owner: android.arch.lifecycle.LifecycleOwner): MaybeSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//    return this.autoDisposable(AndroidLifecycleScopeProvider.from(owner))
}

fun <T> Observable<T>.autoDispose(owner: android.arch.lifecycle.LifecycleOwner): ObservableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
}

fun <T> Flowable<T>.autoDispose(owner: android.arch.lifecycle.LifecycleOwner): FlowableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
}


fun <T:Any> Maybe<T>.autoSubscribeBy(owner: android.arch.lifecycle.LifecycleOwner?=null,
                                     onError:(Throwable)->Unit = {},
                                     onComplete:()->Unit = {},
                                     onSuccess:(T)->Unit = {}
): Disposable {
    if (owner==null){
        return this.subscribe( onSuccess,onError, onComplete)
    }else{
        return this.autoDispose(owner).subscribe(onSuccess, onError, onComplete)
    }
}


fun <T:Any> Flowable<T>.autoSubscribeBy(owner: android.arch.lifecycle.LifecycleOwner?=null,
                                     onError:(Throwable)->Unit = {},
                                     onComplete:()->Unit = {},
                                     onSuccess:(T)->Unit = {}
): Disposable {
    if (owner==null){
        return this.subscribe( onSuccess,onError, onComplete)
    }else{
        return this.autoDispose(owner).subscribe(onSuccess, onError, onComplete)
    }
}


fun <T:Any> Observable<T>.autoSubscribeBy(owner: android.arch.lifecycle.LifecycleOwner?=null,
                                        onError:(Throwable)->Unit = {},
                                        onComplete:()->Unit = {},
                                        onSuccess:(T)->Unit = {}
): Disposable {
    if (owner==null){
        return this.subscribe( onSuccess,onError, onComplete)
    }else{
        return this.autoDispose(owner).subscribe(onSuccess, onError, onComplete)
    }
}