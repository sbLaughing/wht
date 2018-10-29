package com.inwandou.wandou.functions.find

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentFindBinding
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * 描述:
 *
 * Created by and on 2018/10/14.
 */
class FindFragment : WDFragment<FragmentFindBinding>() {


    companion object {
        val tabs = listOf("推荐","网红","明星","动物","旅游","商业")

        fun newInstance():FindFragment = FindFragment()
    }


    override fun initView() {
        setSwipeBackEnable(false)

//        tabs.forEach {
//            tab_layout.addTab(tab_layout.newTab().setText(it))
//        }


        view_pager.adapter = object :FragmentPagerAdapter(childFragmentManager){
            override fun getItem(position: Int): Fragment {
                return SubFindFragment.newInstance()
            }

            override fun getCount(): Int = tabs.size

            override fun getPageTitle(position: Int): CharSequence? {
                return tabs[position]
            }
        }

        tab_layout.setupWithViewPager(view_pager)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_find
}