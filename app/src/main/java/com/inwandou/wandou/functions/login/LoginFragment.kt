package com.inwandou.wandou.functions.login

import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentLoginBinding
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

    override fun getLayoutRes(): Int = R.layout.fragment_login

    override fun initView() {
        setSwipeBackEnable(true)
        back_btn.setOnClickListener {
            pop()
        }

    }
}