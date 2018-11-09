package com.alien.newsdk.extensions

import android.view.View
import android.view.ViewGroup
import com.alien.newsdk.util.ScreenUtil

/**
 * 描述:
 *
 * Created by and on 2018/11/3.
 */
inline fun <reified T:View> ViewGroup.forEachChild(each:(View)->Unit){
    val count = this.childCount
    if (count<1) return

    for (i in 0 until childCount){
        if (this.getChildAt(i) !is T) continue
        else each(this.getChildAt(i))
    }
}

fun Int.dp2px(): Int {
    return ScreenUtil.dip2px(this.toFloat())
}