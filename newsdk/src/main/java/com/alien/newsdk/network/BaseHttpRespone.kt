package com.alien.newsdk.network

/**
 * 描述:
 *
 * Created by Alien on 2017/12/8.
 */
interface BaseHttpResponeImpl<T> {
    fun getApiData(): T? {
        return null
    }

    fun isError(): Boolean

    fun getErroCode(): Int?

    fun getErrorMsg(): String?
}

interface BaseHttpRespone{
    fun isError(): Boolean

    fun getErroCode(): Int?

    fun getErrorMsg(): String?
}