package com.lovewandou.wd.functions.login

import android.databinding.ObservableField
import com.alien.newsdk.base.BaseVM
import com.alien.newsdk.network.async
import com.alien.newsdk.network.transformData
import com.lovewandou.wd.BR
import com.lovewandou.wd.models.AppData
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

    fun login(): Maybe<LoginResp> {
        return RequestProvider.accountRequest.login(accountString.get()?:"",passwordString.get()?:"")
                .async()
                .transformData()
                .doOnSuccess {
                    AppData.token = it.token
                    AppData.appVM.apply {
                        accountInfo = it.user
                        notifyPropertyChanged(BR.accountInfo)
                    }
                }
    }
}