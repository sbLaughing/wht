package com.lovewandou.wd.functions.myAttends

import com.alien.newsdk.base.BaseVM
import com.alien.newsdk.network.async
import com.lovewandou.wd.models.AppData
import com.lovewandou.wd.models.data.UserIdReq
import com.lovewandou.wd.models.data.UserInfo
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/11/4.
 */
class MyAttendsVM:BaseVM() {

    fun getMyAttendsUser(): Maybe<List<UserInfo>> {
        return RequestProvider.userRequest.getAttendUsers(AppData.mGson.toJson(UserIdReq(AppData.appVM.accountInfo.user_id)))
                .async()
    }
}