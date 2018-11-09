package com.lovewandou.wd.functions.home

import com.alien.newsdk.network.async
import com.lovewandou.wd.base.BaseSkipVM
import com.lovewandou.wd.models.AppData
import com.lovewandou.wd.models.data.BaseRequest
import com.lovewandou.wd.models.data.UserPostInfo
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/11/6.
 */
class HomeVM :BaseSkipVM(){

    fun getHomeFeed(): Maybe<List<UserPostInfo>> {
        val req = BaseRequest(skip,limit)
        return RequestProvider.userRequest.getHomeFeed(AppData.mGson.toJson(req))
                .async()
    }

}