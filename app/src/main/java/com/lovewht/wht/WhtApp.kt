package com.lovewht.wht

import android.app.Application
import com.alien.newsdk.util.ScreenUtil

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class WhtApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ScreenUtil.init(this)
    }
}