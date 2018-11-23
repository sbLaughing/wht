package com.lovewandou.wd.functions.find

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.alien.newsdk.extensions.autoSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentFindBinding
import com.lovewandou.wd.functions.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * 描述:
 *
 * Created by and on 2018/10/14.
 */
class FindFragment : WDFragment<FragmentFindBinding>() {


    companion object {
        fun newInstance(): FindFragment = FindFragment()
    }

    val labelsList = mutableListOf<String>()
    val findVM = FindVM()

    val mFragments = mutableMapOf<Int,SubFindFragment>()

    var mViewPagerAdapter: FragmentStatePagerAdapter? = null
    override fun initView() {
        setSwipeBackEnable(false)

        mViewPagerAdapter = object : FragmentStatePagerAdapter(childFragmentManager) {


            override fun getItem(position: Int): Fragment {
                return SubFindFragment.newInstance(labelsList[position])
            }

            override fun getCount(): Int {
                return labelsList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return labelsList[position]
            }
        }
        view_pager.adapter = mViewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
        search_bar.setEditEnabled(false)
        search_bar.setOnClickListener {
            getSupportParentFragment()?.extraTransaction()?.startDontHideSelf(SearchFragment.newInstance())
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        findVM.getTags()
                .autoSubscribeBy(this@FindFragment) {
                    labelsList.clear()
                    labelsList.addAll(it)
                    mViewPagerAdapter?.notifyDataSetChanged()
                }

    }

    override fun getLayoutRes(): Int = R.layout.fragment_find
}