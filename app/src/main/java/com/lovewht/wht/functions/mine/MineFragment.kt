package com.lovewht.wht.functions.mine

import com.lovewht.wht.R
import com.lovewht.wht.base.WhtFragment
import com.lovewht.wht.databinding.FragmentMineBinding

/**
 * 描述:
 *
 * Created by and on 2018/10/14.
 */
class MineFragment : WhtFragment<FragmentMineBinding>() {

    companion object {
            fun newInstance():MineFragment = MineFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_mine

    override fun initView() {
    }
}