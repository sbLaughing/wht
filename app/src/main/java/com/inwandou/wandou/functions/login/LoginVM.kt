package com.inwandou.wandou.functions.login

import android.databinding.ObservableField
import com.alien.newsdk.base.BaseVM
import com.alien.newsdk.network.async
import com.alien.newsdk.network.transformData
import com.inwandou.wandou.models.data.LoginResp
import com.inwandou.wandou.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
class LoginVM : BaseVM() {

    val accountString = ObservableField<String>("")
    val passwordString = ObservableField<String>("")

    fun login(): Maybe<LoginResp> {
        return RequestProvider.accountRequest.login(accountString.get()?:"",passwordString.get()?:"")
                .async()
                .transformData()
    }
}