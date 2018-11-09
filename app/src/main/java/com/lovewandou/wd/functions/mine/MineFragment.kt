package com.lovewandou.wd.functions.mine

import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentMineBinding
import com.lovewandou.wd.functions.login.LoginSelectFragment
import com.lovewandou.wd.functions.myAttends.MyAttendsFragment
import com.lovewandou.wd.models.AppData
import kotlinx.android.synthetic.main.fragment_mine.*

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
        setSwipeBackEnable(false)

        mBinding.appvm = AppData.appVM

        attends_tv.setOnClickListener {
            getSupportParentFragment()?.start(MyAttendsFragment.newInstance())
        }
        to_login_tv.setOnClickListener {
            getSupportParentFragment()?.extraTransaction()?.startDontHideSelf(LoginSelectFragment.newInstance())
//            getSupportParentFragment()?.start(LoginSelectFragment.newInstance())

        }

    }


}