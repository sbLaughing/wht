package com.lovewandou.wd.functions.login

import android.app.Activity
import android.os.Bundle
import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentLoginBinding
import com.lovewandou.wd.functions.pwd.FindPwdFragment
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
                setFragmentResult(Activity.RESULT_OK, Bundle())
                pop()
            }
        }

        find_pwd_tv.setOnClickListener {
            start(FindPwdFragment.newInstance())
        }

        back_btn.setOnClickListener {
            pop()
        }



    }
}