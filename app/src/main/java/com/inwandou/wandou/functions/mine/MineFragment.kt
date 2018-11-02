package com.inwandou.wandou.functions.mine

import android.transition.TransitionInflater
import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentMineBinding
import com.inwandou.wandou.functions.login.LoginSelectFragment
import com.inwandou.wandou.models.AppData
import kotlinx.android.synthetic.main.fragment_mine.*
import me.yokeyword.fragmentation.anim.DefaultNoAnimator

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
        to_login_tv.setOnClickListener {

            val fragment = LoginSelectFragment.newInstance()
            fragment.apply {
                enterTransition = TransitionInflater.from(this@MineFragment.activity).inflateTransition(R.transition.bottom_enter)
                exitTransition = enterTransition
                fragmentAnimator = DefaultNoAnimator()
            }
            getSupportParentFragment()?.extraTransaction()?.start(LoginSelectFragment.newInstance())
        }

    }
}