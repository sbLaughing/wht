package com.lovewandou.wd.extension

import android.view.View
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.functions.login.LoginSelectFragment
import com.lovewandou.wd.models.AppData

/**
 * 描述:
 *
 * Created by and on 2018/11/23.
 */
fun View.checkLoginOnClick(fragment:WDFragment<*>?,onLogin:()->Unit){
    this.setOnClickListener {
        if (AppData.appVM.isLogin) onLogin()
        else{
            fragment?.extraTransaction()?.startDontHideSelf(LoginSelectFragment.newInstance())
        }
    }

}