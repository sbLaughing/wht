package com.inwandou.wandou.network

import com.alien.newsdk.network.NetManager
import com.alien.newsdk.network.NetProvider
import com.alien.newsdk.network.RequestHandler
import com.inwandou.wandou.BuildConfig
import java.nio.charset.Charset

/**
 * 描述:
 *
 * Created by Alien on 2018/2/24.
 */
object RequestProvider {
    private val UTF8 = Charset.forName("UTF-8")

    private val retrofit by lazy {
        NetManager.getRetrofit(object : NetProvider {
            override fun connectTimeout(): Long {
                return 60
            }

            override fun getBaseUrl(): String = "http://47.106.242.220:8088/"

            override fun configLogEnable(): Boolean = BuildConfig.DEBUG

            override fun configHandler(): RequestHandler? {
//                return object : DefaultRequestHandler() {
//                    override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
//
//                        val newrequest = request.newBuilder()
////                                .addHeader("Accept", "application/json")
////                                .addHeader("Authorization", "Bearer ${AppData.sTokenCache.accessToken}")
//                                .addHeader("os", android.os.Build.VERSION.RELEASE)
//                                .addHeader("device", android.os.Build.MODEL)
//                                .addHeader("version", BuildConfig.VERSION_NAME)
//                                .addHeader("versionCode",BuildConfig.VERSION_CODE.toString())
//                                .build()
//                        return newrequest
//                    }
//
//                    override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
//                        if (401 == response.code()) {
//                            throw ApiConnectExceptioin(response.code(), "登录已过期,请重新登录!")
//                        } else if (403 == response.code()) {
//                            throw ApiConnectExceptioin(response.code(), "没有访问权限")
//                        } else if (404 == response.code()) {
//                            throw ApiConnectExceptioin(response.code(), "地址不存在")
//                        } else if (503 == response.code()) {
//                            throw ApiConnectExceptioin(response.code(), "服务器升级中...")
//                        } else if (response.code() > 300) {
//                            throw ApiConnectExceptioin(response.code(), "服务器内部错误")
//                        }
//
//                        return super.onAfterRequest(response, chain)
//                    }
//                }
                return null
            }
        })
    }


    val accountRequest by lazy {
        retrofit.create(AccountRequest::class.java)
    }
}