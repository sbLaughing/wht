package com.lovewandou.wd.network

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
}