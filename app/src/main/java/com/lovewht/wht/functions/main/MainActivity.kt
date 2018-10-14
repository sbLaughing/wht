package com.lovewht.wht.functions.main

import com.lovewht.wht.R
import com.lovewht.wht.base.WhtActivity
import com.lovewht.wht.databinding.ActivityMainBinding

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class MainActivity : WhtActivity<ActivityMainBinding>() {
    override fun initView() {

        var fragment = findFragment(MainFragment::class.java)
        if (fragment == null) {
            fragment = MainFragment.newInstance()
            loadRootFragment(R.id.container, fragment)
        }

    }

    override fun getLayoutRes(): Int = R.layout.activity_main
}