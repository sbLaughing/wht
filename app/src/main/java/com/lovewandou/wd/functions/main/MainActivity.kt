package com.lovewandou.wd.functions.main

import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDActivity
import com.lovewandou.wd.databinding.ActivityMainBinding

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class MainActivity : WDActivity<ActivityMainBinding>() {
    override fun initView() {
        var fragment = findFragment(MainFragment::class.java)
        if (fragment == null) {
            fragment = MainFragment.newInstance()
            loadRootFragment(R.id.container, fragment)
        }

    }

    override fun getLayoutRes(): Int = R.layout.activity_main
}