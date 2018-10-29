package com.inwandou.wandou.functions.main

import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDActivity
import com.inwandou.wandou.databinding.ActivityMainBinding

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