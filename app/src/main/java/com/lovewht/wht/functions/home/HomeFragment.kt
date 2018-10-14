package com.lovewht.wht.functions.home

import com.lovewht.wht.R
import com.lovewht.wht.base.WhtFragment
import com.lovewht.wht.databinding.FragmentHomeBinding

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class HomeFragment : WhtFragment<FragmentHomeBinding>() {

    companion object {
            fun newInstance():HomeFragment = HomeFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initView() {
    }
}