package com.lovewandou.wd.network

import com.lovewandou.wd.models.data.BaseResponse
import com.lovewandou.wd.models.data.TagsInfo
import com.lovewandou.wd.models.data.UserInfo
import com.lovewandou.wd.models.data.UserPostInfo
import io.reactivex.Maybe
import retrofit2.http.*

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

    @POST("productInfo.do")
    @FormUrlEncoded
    fun getProductInfo(@Field("json")json:String):Maybe<UserPostInfo>

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


    @GET("share.html")
    fun getShareUrl(@Query("post_id")postId:String):Maybe<String>

}