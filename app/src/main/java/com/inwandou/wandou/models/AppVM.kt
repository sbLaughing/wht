package com.inwandou.wandou.models

import android.databinding.Bindable
import com.alien.newsdk.base.BaseVM
import com.inwandou.wandou.BR
import com.inwandou.wandou.models.data.UserInfo

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
class AppVM : BaseVM() {

    @Bindable
    var userInfo:UserInfo = UserInfo()
    set(value) {
        field = value
        notifyPropertyChanged(BR.userInfo)
    }


}