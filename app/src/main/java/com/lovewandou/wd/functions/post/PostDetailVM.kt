package com.lovewandou.wd.functions.post

import com.alien.newsdk.base.BaseVM
import com.alien.newsdk.network.async
import com.alien.newsdk.network.transformData
import com.lovewandou.wd.models.AppData
import com.lovewandou.wd.models.data.PostIdReq
import com.lovewandou.wd.models.data.UserPostInfo
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/12/13.
 */
class PostDetailVM : BaseVM(){

    fun getPostDetail(id:String): Maybe<UserPostInfo> {
        return RequestProvider.userRequest.getProductInfo(AppData.mGson.toJson(PostIdReq(id)))
                .async()
                .transformData()
    }
}