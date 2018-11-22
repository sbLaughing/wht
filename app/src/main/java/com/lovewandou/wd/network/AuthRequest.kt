package com.lovewandou.wd.network

import com.lovewandou.wd.models.data.WeiboUserInfo
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 描述:
 *
 * Created by and on 2018/11/15.
 */
interface AuthRequest {

    @GET("https://api.weibo.com/2/users/show.json")
    fun getWeiboProfile(@Query("access_token")access_token:String,
                        @Query("uid")uid:String):Maybe<WeiboUserInfo>
}