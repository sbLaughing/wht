package com.lovewandou.wd.network

import com.lovewandou.wd.models.data.BaseResponse
import com.lovewandou.wd.models.data.TagsInfo
import com.lovewandou.wd.models.data.UserInfo
import com.lovewandou.wd.models.data.UserPostInfo
import io.reactivex.Maybe
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 描述:
 *
 * Created by and on 2018/11/3.
 */
interface UserRequest {

    @POST("queryFeed.do")
    @FormUrlEncoded
    fun getHomeFeed(@Field("json")json:String): Maybe<List<UserPostInfo>>


    @POST("seachUsers.do")
    @FormUrlEncoded
    fun searchUser(@Field("json")json:String): Maybe<List<UserInfo>>


    @POST("queryLabel.do")
    fun getTags(): Maybe<List<TagsInfo>>


    @POST("info.do")
    @FormUrlEncoded
    fun getUserInfo(@Field("json")json:String):Maybe<UserInfo>


    @POST("queryListByUid.do")
    @FormUrlEncoded
    fun getPostsFromUser(@Field("json")json:String):Maybe<List<UserPostInfo>>

    @POST("attend.do")
    @FormUrlEncoded
    fun attendUser(@Field("json")json:String):Maybe<BaseResponse>

    @POST("cancelAttend.do")
    @FormUrlEncoded
    fun cancelAttendUser(@Field("json")json:String):Maybe<BaseResponse>


    @POST("queryAttendUsers.do")
    @FormUrlEncoded
    fun getAttendUsers(@Field("json")json:String):Maybe<List<UserInfo>>

    @POST("setAutoAttend.do")
    @FormUrlEncoded
    fun setAutoAttend(@Field("json")json:String):Maybe<BaseResponse>


}