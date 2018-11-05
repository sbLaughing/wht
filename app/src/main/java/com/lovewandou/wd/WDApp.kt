package com.lovewandou.wd

import android.app.Application
import com.alien.newsdk.util.ScreenUtil

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class WDApp : Application() {

    companion object {
        lateinit var context:Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this@WDApp
        ScreenUtil.init(this)
    }
}