package com.lovewandou.wd.functions.main

import android.Manifest
import android.content.Intent
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDActivity
import com.lovewandou.wd.databinding.ActivityMainBinding
import com.lovewandou.wd.extension.rxrequestPermission
import com.lovewandou.wd.extension.showToast
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class MainActivity : WDActivity<ActivityMainBinding>(), WbShareCallback {
    override fun onWbShareFail() {
        showToast("微博分享失败")
    }

    override fun onWbShareCancel() {
    }

    override fun onWbShareSuccess() {
        showToast("微博分享成功")
    }

    val mSsoHandler: SsoHandler by lazy {
        SsoHandler(this@MainActivity)
    }

    val shareHandler by lazy {
        val ret = WbShareHandler(this@MainActivity)
        ret.registerApp()
        return@lazy ret
    }

    var wxApi:IWXAPI?=null

    override fun initView() {
        var fragment = findFragment(MainFragment::class.java)
        if (fragment == null) {
            fragment = MainFragment.newInstance()
            loadRootFragment(R.id.container, fragment)
        }

        this.rxrequestPermission("需要文件存储权限", "", Manifest.permission.WRITE_EXTERNAL_STORAGE) {}
        initWx()
    }

    private fun initWx() {
        wxApi = WXAPIFactory.createWXAPI(this@MainActivity,"wx80e50eae7be343dc").apply {
            registerApp("wx80e50eae7be343dc")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==1 ){
            shareHandler.doResultIntent(intent,this@MainActivity)
        }else{
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data)
        }

    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }
}