package com.lovewandou.wd.functions.find

import com.alien.newsdk.base.BaseVM
import com.alien.newsdk.network.async
import com.lovewandou.wd.models.AppData
import com.lovewandou.wd.models.data.SearchReq
import com.lovewandou.wd.models.data.UserInfo
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/11/3.
 */
class FindVM :BaseVM() {



    fun searchUsers(key:String?=null,tag:String?=null): Maybe<List<UserInfo>> {
        return RequestProvider.userRequest.searchUser(AppData.mGson.toJson(SearchReq(keyword = key,tag = tag)))
                .async()
    }

    fun getTags(): Maybe<List<String>>{
        return RequestProvider.userRequest.getTags()
                .async()
                .map { list ->
                    list.map { it.name }
                }
    }
}