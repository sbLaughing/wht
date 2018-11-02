package com.lovewandou.wd

import android.app.Application
import com.alien.newsdk.util.ScreenUtil

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class WDApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ScreenUtil.init(this)
    }
}