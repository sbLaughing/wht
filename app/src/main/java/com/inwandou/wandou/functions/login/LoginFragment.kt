package com.inwandou.wandou.functions.login

import com.alien.newsdk.network.safeSubscribeBy
import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentLoginBinding
import com.inwandou.wandou.models.AppData
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