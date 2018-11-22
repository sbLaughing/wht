package com.alien.newsdk.extensions

import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.MaybeSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * 描述:
 *
 * Created by and on 2018/11/22.
 */
fun <T> Maybe<T>.autoDispose(owner: LifecycleOwner): MaybeSubscribeProxy<T> {
    return this.autoDisposable(AndroidLifecycleScopeProvider.from(owner))
}

fun <T> Observable<T>.autoDispose(owner: LifecycleOwner): ObservableSubscribeProxy<T> {
    return this.autoDisposable(AndroidLifecycleScopeProvider.from(owner))
}


fun <T:Any> Maybe<T>.safeSubscribeByNew(owner: LifecycleOwner?=null,
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
