package com.lovewandou.wd.functions.pwd

import android.databinding.ObservableField
import com.alien.newsdk.base.BaseVM
import com.alien.newsdk.network.async
import com.alien.newsdk.network.transformData
import com.lovewandou.wd.models.data.BaseResponse
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/11/20.
 */
class FindPwdVM : BaseVM() {

    val mobileObservable = ObservableField<String>("")
    val capthObservable = ObservableField<String>("")
    val pwdObservable = ObservableField<String>("")

    fun send(): Maybe<BaseResponse> {
        return RequestProvider.accountRequest.sendCode(mobileObservable.get()?:"")
                .async()
                .transformData()
    }

    fun submit(): Maybe<BaseResponse> {
        if (mobileObservable.get().isNullOrEmpty()
                || capthObservable.get().isNullOrEmpty()
                || pwdObservable.get().isNullOrEmpty()){
            return Maybe.empty()
        }

        return RequestProvider.accountRequest.restPwd(mobileObservable.get(),capthObservable.get(),pwdObservable.get())
                .async()
                .transformData()
    }


}