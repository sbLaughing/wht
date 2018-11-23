package com.lovewandou.wd.extension

import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.functions.login.LoginSelectFragment
import com.lovewandou.wd.models.AppData

/**
 * 描述:
 *
 * Created by and on 2018/11/23.
 */
fun WDFragment<*>.checkLogin(onLogin:()->Unit){
    if (AppData.appVM.isLogin) onLogin()
    else{
        this.extraTransaction()?.startDontHideSelf(LoginSelectFragment.newInstance())
    }
}

