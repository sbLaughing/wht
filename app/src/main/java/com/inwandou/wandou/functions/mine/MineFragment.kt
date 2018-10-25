package com.inwandou.wandou.functions.mine

import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentMineBinding

/**
 * 描述:
 *
 * Created by and on 2018/10/14.
 */
class MineFragment : WDFragment<FragmentMineBinding>() {

    companion object {
            fun newInstance():MineFragment = MineFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_mine

    override fun initView() {
    }
}