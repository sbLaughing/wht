package com.alien.newsdk.network

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
/********************** Rx相关 **********************/
fun <T> Flowable<T>.async(withDelay: Long = 0): Flowable<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.async(withDelay: Long = 0): Maybe<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<out BaseHttpResponeImpl<T>>.transformDataImpl(): Maybe<T> = this.map {
    if (it.isError()) throw ApiException(it.getErroCode(), it.getErrorMsg())
    else it.getApiData()
}

fun <T> Single<out BaseHttpResponeImpl<T>>.transformDataImpl(): Single<T> = this.map {
    if (it.isError()) throw ApiException(it.getErroCode(), it.getErrorMsg())
    else it.getApiData()
}


fun <T:BaseHttpRespone> Maybe<T>.transformData(): Maybe<T> = this.map {
    if (it.isError()) throw ApiException(it.getErroCode(), it.getErrorMsg())
    else it
}


fun <T> Maybe<T>.safeSubscribeBy(
        onError: (Throwable) -> Unit = { _ -> },
        onComplete: () -> Unit={},
        onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError,onComplete)

fun <T> Maybe<T>.maybeSubscribeBy(
        onError: (Throwable) -> Unit = { _ -> },
        onComplete: () -> Unit={}
): Disposable = subscribe({onComplete()}, onError,onComplete)


fun <T> Single<T>.safeSubscribeBy(
        onError: (Throwable) -> Unit = { _ -> },
        onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError)

fun <T> Flowable<T>.safeSubscribeBy(
        onError: (Throwable) -> Unit = { _ -> },
        onComplete:()->Unit = {},
        onNext: (T) -> Unit = {}
): Disposable = subscribe(onNext, onError,onComplete)

fun <T> Observable<T>.safeSubscribeBy(
        onError: (Throwable) -> Unit = { _ -> },
        onComplete:()->Unit = {},
        onNext: (T) -> Unit = {}
): Disposable = subscribe(onNext, onError,onComplete)