package com.inwandou.wandou.functions.main

import android.os.Bundle
import com.alien.newsdk.widget.tab.BottomBar
import com.alien.newsdk.widget.tab.BottomBarTab
import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentMainBinding
import com.inwandou.wandou.functions.find.FindFragment
import com.inwandou.wandou.functions.home.HomeFragment
import com.inwandou.wandou.functions.mine.MineFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class MainFragment : WDFragment<FragmentMainBinding>() {


    val mFragments = arrayOfNulls<WDFragment<*>>(3)


    override fun getLayoutRes(): Int = R.layout.fragment_main

    override fun initView() {
        setSwipeBackEnable(false)

        bottomBar
                .addItem(BottomBarTab(_mActivity, R.drawable.home_un, 32,null).apply { setSelectRes(R.drawable.home_se) })
                .addItem(BottomBarTab(_mActivity, R.drawable.find_un, 32,null).apply { setSelectRes(R.drawable.find_se) })
                .addItem(BottomBarTab(_mActivity, R.drawable.personal_un, 32,null).apply { setSelectRes(R.drawable.personal_se) })
        bottomBar.setOnTabSelectedListener(object : BottomBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int, prePosition: Int) {
                val pf = mFragments[position]
                val prepf = mFragments[prePosition]
                if (pf == null || prepf == null) return
                showHideFragment(pf, prepf)

            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabReselected(position: Int) {
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val firstFragment = findChildFragment(HomeFragment::class.java)
        if (firstFragment == null) {
            mFragments[0] = HomeFragment.newInstance()
            mFragments[1] = FindFragment.newInstance()
            mFragments[2] = MineFragment.newInstance()

            loadMultipleRootFragment(R.id.fragment_container, 0,
                    mFragments[0], mFragments[1], mFragments[2])

        } else {
            mFragments[0] = firstFragment
            mFragments[1] = findChildFragment(FindFragment::class.java)
            mFragments[2] = findChildFragment(MineFragment::class.java)
        }
    }

    companion object {
            fun newInstance(): MainFragment = MainFragment()
    }

}