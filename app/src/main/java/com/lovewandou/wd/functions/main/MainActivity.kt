package com.lovewandou.wd.functions.main

import android.content.Intent
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDActivity
import com.lovewandou.wd.databinding.ActivityMainBinding
import com.sina.weibo.sdk.auth.sso.SsoHandler

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class MainActivity : WDActivity<ActivityMainBinding>() {

    val mSsoHandler: SsoHandler by lazy {
        SsoHandler(this@MainActivity)
    }

    override fun initView() {
        var fragment = findFragment(MainFragment::class.java)
        if (fragment == null) {
            fragment = MainFragment.newInstance()
            loadRootFragment(R.id.container, fragment)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSsoHandler.authorizeCallBack(requestCode, resultCode, data)
    }

    override fun getLayoutRes(): Int = R.layout.activity_main
}