package com.alien.newsdk.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 描述:
 *
 * Created by Alien on 2017/12/15.
 */
open class DefaultRequestHandler : RequestHandler {

    override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
        var e: ApiConnectExceptioin? = null
        if (401 == response.code()) {
            throw ApiConnectExceptioin(response.code(), "登录已过期,请重新登录!")
        } else if (403 == response.code()) {
            throw ApiConnectExceptioin(response.code(), "没有访问权限")
        } else if (404 == response.code()) {
            throw ApiConnectExceptioin(response.code(), "地址不存在")
        } else if (503 == response.code()) {
            throw ApiConnectExceptioin(response.code(), "服务器升级中...")
        } else if (response.code() > 300) {
            val message = response.body()?.string()
            throw ApiConnectExceptioin(response.code(), "服务器内部错误")
        }
        return super.onAfterRequest(response, chain)
    }
}