package com.lovewandou.wd.functions.login

import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentLoginBinding
import com.lovewandou.wd.models.AppData
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * 描述:
 *
 * Created by and on 2018/10/27.
 */
class LoginFragment : WDFragment<FragmentLoginBinding>() {
    companion object {
            fun newInstance():LoginFragment = LoginFragment()
    }

    val vm = LoginVM()
    override fun getLayoutRes(): Int = R.layout.fragment_login

    override fun initView() {
        setSwipeBackEnable(true)


        mBinding.vm = vm

        login_tv.setOnClickListener {_->
            vm.login().safeSubscribeBy {
                AppData.appVM.apply {
                    userInfo = it.user
                    notifyChange()
                }
                pop()
            }
        }

        back_btn.setOnClickListener {
            pop()
        }

    }
}