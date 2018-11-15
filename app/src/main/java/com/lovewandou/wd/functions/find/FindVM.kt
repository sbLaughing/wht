package com.lovewandou.wd.functions.find

import com.alien.newsdk.network.async
import com.lovewandou.wd.base.BaseSkipVM
import com.lovewandou.wd.models.AppData
import com.lovewandou.wd.models.data.BaseResponse
import com.lovewandou.wd.models.data.SearchReq
import com.lovewandou.wd.models.data.UserIdReq
import com.lovewandou.wd.models.data.UserInfo
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/11/3.
 */
class FindVM :BaseSkipVM() {

    var currentKey :String? = null

    fun searchUsers(key:String ?= currentKey): Maybe<List<UserInfo>> {
        val req = SearchReq(keyword = key)
        req.skip = skip
        req.limit = limit
        return RequestProvider.userRequest.searchUser(AppData.mGson.toJson(req))
                .async()
    }

    fun searchUsers(key:String?=null,tag:String?=null): Maybe<List<UserInfo>> {
        val req = SearchReq(keyword = key,tag = tag)
        req.skip = skip
        req.limit = limit
        return RequestProvider.userRequest.searchUser(AppData.mGson.toJson(req))
                .async()
    }

    fun getTags(): Maybe<List<String>>{
        return RequestProvider.userRequest.getTags()
                .async()
                .map { list ->
                    list.map { it.name }
                }
    }

    fun autoAttend(userid:String): Maybe<BaseResponse> {
        return RequestProvider.userRequest.setAutoAttend(AppData.mGson.toJson(UserIdReq(userid)))
                .async()
    }
}