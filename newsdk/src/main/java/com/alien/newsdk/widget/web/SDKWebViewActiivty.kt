package com.alien.newsdk.widget.web

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alien.newsdk.R
import com.alien.newsdk.util.StatusBarUtil
import com.alien.newsdk.widget.toolbar.MToolbar

/**
 * 描述:
 *
 * Created by Alien on 2017/12/29.
 */
class SDKWebViewActiivty:AppCompatActivity() {

    private val progressView by lazy {
        findViewById<ProgressWebView>(R.id.mProgressWebview)
    }

    companion object {
        val EXTRA_URL = "url"
        val EXTRA_LIGHT = "lightmode"
        val EXTRA_WITH_PROGRESS = "withprogress"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk_webview)
        loadData()

        if (intent.getBooleanExtra(EXTRA_LIGHT,false)) {
            StatusBarUtil.StatusBarLightMode(this)
        }
    }

    fun loadData() {

        val toolbar = findViewById<MToolbar>(R.id.mComToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        if(!intent.getBooleanExtra(EXTRA_WITH_PROGRESS,true)){
            progressView.hideProgress()
        }

        progressView.setIWebOutter(WebManager.iwebOut)

//        progressView.setIWebOutter(WebManager.iwebOut?:(object : WebCallbackAdapter(){
//            override fun onTitleGet(title: String?) {
//                toolbar?.title = title
//            }
//        }))
        progressView?.loadUrl(intent.getStringExtra(EXTRA_URL))

    }

    override fun onBackPressed() {
        if (progressView?.onBack()==false){
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        progressView?.webView?.removeAllViews()
        progressView?.webView?.destroy()
        progressView?.removeView(progressView?.webView)
        super.onDestroy()
    }

}