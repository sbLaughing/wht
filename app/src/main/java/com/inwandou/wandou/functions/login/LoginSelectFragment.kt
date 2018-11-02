package com.inwandou.wandou.functions.login

import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentLoginSelectBinding
import kotlinx.android.synthetic.main.fragment_login_select.*

/**
 * 描述:
 *
 * Created by and on 2018/11/2.
 */
class LoginSelectFragment : WDFragment<FragmentLoginSelectBinding>() {

    companion object {
            fun newInstance():LoginSelectFragment = LoginSelectFragment()
    }


    override fun getLayoutRes(): Int = R.layout.fragment_login_select

    override fun initView() {
        close_btn.setOnClickListener {
            pop()
        }

        account_login_tv.setOnClickListener {
            start(LoginFragment.newInstance())
        }
    }
}