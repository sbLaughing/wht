package com.lovewandou.wd.network

import com.lovewandou.wd.models.data.BaseResponse
import com.lovewandou.wd.models.data.LoginResp
import io.reactivex.Maybe
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
interface AccountRequest {


    @POST("login.do")
    fun login(@Query("mobile")mobile:String,@Query("password")password:String):Maybe<LoginResp>

    @POST("loginByWeibo.do")
    fun weiboLogin(
            @Query("uid")uid:String,
            @Query("name")name:String,
            @Query("avatar_hd")avatar_hd:String
            ):Maybe<LoginResp>


    @POST("sendCode.do")
    fun sendCode(@Query("mobile")mobile:String):Maybe<BaseResponse>

    @POST("restPwd.do")
    fun restPwd(@Query("mobile")mobile:String?,
                @Query("code")code:String?,
                @Query("password")password:String?):Maybe<BaseResponse>
}