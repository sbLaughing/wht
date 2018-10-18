package com.lovewht.wht.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.alien.ksdk.base.SDKActivity
import com.alien.newsdk.util.StatusBarUtil

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
abstract class WhtActivity<T:ViewDataBinding> : SDKActivity<T>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.StatusBarLightMode(this@WhtActivity)
    }
}