package com.lovewandou.wd

import android.app.Application
import com.alien.newsdk.util.ScreenUtil
import com.lovewandou.wd.weibo.Constants
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import com.tencent.stat.StatConfig
import com.tencent.stat.StatService


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
        WbSdk.install(this, AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE))
//        Beta.canShowApkInfo =false
//        Bugly.init(this, "071230fd0c", false)
        StatConfig.setDebugEnable(BuildConfig.DEBUG)
        StatService.registerActivityLifecycleCallbacks(this@WDApp)
    }
}