package com.lovewandou.wd.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.alien.newsdk.base.SDKActivity
import com.alien.newsdk.util.StatusBarUtil

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
abstract class WDActivity<T:ViewDataBinding> : SDKActivity<T>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.StatusBarLightMode(this@WDActivity)
    }


}