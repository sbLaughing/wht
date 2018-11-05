package com.lovewandou.wd.models

import com.alien.newsdk.util.ACache
import com.google.gson.Gson
import com.lovewandou.wd.WDApp

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
object AppData {

    val mGson by lazy {
        Gson()
    }
    val appVM by lazy {
        AppVM()
    }

    var token :String=""
        get() {
            if (field.isNullOrEmpty()){
                val ret = mCommonCache.getAsString("token")
                if (ret.isNullOrEmpty()) return ""
                field = ret
                return field
            }else return field
        }

        set(value) {
            field = value
            mCommonCache.put("token",field)
        }

    val mCommonCache by lazy {
        ACache.get(WDApp.context,"common")
    }

}