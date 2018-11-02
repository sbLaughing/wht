package com.alien.newsdk.network

import okhttp3.CookieJar
import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * 描述:
 *
 * Created by Alien on 2017/12/7.
 */
interface NetProvider {
    fun getBaseUrl(): String
    fun connectTimeout(): Long = 10
    fun readTimeout(): Long=10
    fun writeTimeout(): Long=10
    fun configCookie(): CookieJar? = null
    fun configHandler(): RequestHandler?=null
    fun configInterceptors(): Array<Interceptor>?=null
    fun configLogEnable(): Boolean = false
    fun adapterFactory(): CallAdapter.Factory{
        return RxJava2CallAdapterFactory.create()
    }
}