package com.lovewandou.wd.models

import android.databinding.Bindable
import com.alien.newsdk.base.BaseVM
import com.lovewandou.wd.models.data.AccountInfo

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
class AppVM : BaseVM() {

    @Bindable
    var accountInfo:AccountInfo = (AppData.mCommonCache.getAsObject("accountInfo") as? AccountInfo) ?: AccountInfo()
    set(value) {
        field = value
        AppData.mCommonCache.put("accountInfo",field)
//        notifyPropertyChanged(BR.accountInfo)
//        notifyPropertyChanged(BR.isLogin)
        notifyChange()
    }

    @Bindable
    var isLogin:Boolean = false
        get() {
            return accountInfo.isValid()
        }

}