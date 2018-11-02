package com.alien.newsdk.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


/**
 * 描述：网络拦截
 *
 *
 * Created by ditclear on 2017/7/28.
 */

interface RequestHandler {

    fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request = request

    @Throws(IOException::class)
    fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response = response

}
