package com.alien.newsdk.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述: 暂不支持多BaseUrl
 *
 * Created by Alien on 2017/12/7.
 */
object NetManager {



    fun getRetrofit(netProvider: NetProvider):Retrofit{
            if (netProvider.getBaseUrl().isEmpty())
                throw IllegalStateException("base url can not be empty")
            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

            val builder = Retrofit.Builder()
                    .baseUrl(netProvider.getBaseUrl())
                    .client(getClient(netProvider))
                    .addCallAdapterFactory(netProvider.adapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))

            return builder.build()

    }

    private fun getClient(provider: NetProvider): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(if (provider.connectTimeout() != 0L)
            provider.connectTimeout()
        else
            10, TimeUnit.SECONDS)
        builder.readTimeout(if (provider.readTimeout() != 0L)
            provider.readTimeout()
        else
            10, TimeUnit.SECONDS)
        builder.writeTimeout(if (provider.writeTimeout() != 0L)
            provider.writeTimeout()
        else
            10, TimeUnit.SECONDS)

        val cookieJar = provider.configCookie()
        if (cookieJar != null) {
            builder.cookieJar(cookieJar)
        }
//        provider.configHttps(builder)

        val handler = provider.configHandler()
        if (handler != null) {
            builder.addInterceptor(NetInterceptor(handler))
        }

        val interceptors = provider.configInterceptors()
        if (interceptors?.isNotEmpty()==true) {
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
        }

        if (provider.configLogEnable()) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

}