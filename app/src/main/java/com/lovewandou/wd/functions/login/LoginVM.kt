package com.lovewandou.wd.functions.login

import android.databinding.ObservableField
import com.alien.newsdk.base.BaseVM
import com.alien.newsdk.network.async
import com.alien.newsdk.network.transformData
import com.alien.newsdk.util.RxBus
import com.lovewandou.wd.models.AppData
import com.lovewandou.wd.models.RefreshHomePageEvent
import com.lovewandou.wd.models.data.LoginResp
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
class LoginVM : BaseVM() {

    val accountString = ObservableField<String>("")
    val passwordString = ObservableField<String>("")

    val commonSuc :(LoginResp)->Unit = {
        AppData.token = it.token
        AppData.appVM.apply {
            accountInfo = it.user
            notifyChange()
        }
        RxBus.get().post(RefreshHomePageEvent())
    }

    fun login(): Maybe<LoginResp> {
        return RequestProvider.accountRequest.login(accountString.get()?:"",passwordString.get()?:"")
                .async()
                .transformData()
                .doOnSuccess(commonSuc)
    }


    fun getWeiboUserInfo(token:String,uid:String): Maybe<LoginResp>{
        return RequestProvider.authRequest.getWeiboProfile(token,uid)
                .flatMap {
                    RequestProvider.accountRequest.weiboLogin(it.idstr,it.name,it.profile_image_url)
                }
                .async()
                .doOnSuccess(commonSuc)

    }
}