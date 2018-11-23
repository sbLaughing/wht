package com.alien.newsdk.widget.web

import android.content.Context
import android.content.Intent

/**
 * 描述:
 *
 * Created by Alien on 2017/12/29.
 */
object WebManager {

    var chromeBindSuccess = false

//    fun bindChrome(context: Context): ServiceConnection {
//        val ret = object : CustomTabsServiceConnection() {
//            override fun onCustomTabsServiceConnected(name: ComponentName?, client: CustomTabsClient?) {
//                chromeBindSuccess = true
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//            }
//
//        }
//        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome", ret)
//        return ret
//    }


    class Build constructor(val context: Context?) {
        var url: String? = null
        var lightBar = false
        var withProgressBar = true

        fun open() {
            if (context == null || url.isNullOrEmpty())
                return
//            if (chromeBindSuccess) {
//                val builder = CustomTabsIntent.Builder()
//                builder.build().launchUrl(context, Uri.parse(url))
//            } else {
            val intent = Intent(context, SDKWebViewActiivty::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra(SDKWebViewActiivty.EXTRA_URL, url)
                        putExtra(SDKWebViewActiivty.EXTRA_LIGHT, lightBar)
                        putExtra(SDKWebViewActiivty.EXTRA_WITH_PROGRESS, withProgressBar)
                    }
            context.startActivity(intent)
        }
    }


    var iwebOut: WebCallbackAdapter = WebCallbackAdapter()
}