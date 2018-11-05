package com.lovewandou.wd.functions.login

import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentLoginSelectBinding
import kotlinx.android.synthetic.main.fragment_login_select.*
import me.yokeyword.fragmentation.anim.FragmentAnimator

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


    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return FragmentAnimator(R.anim.v_fragment_enter,R.anim.v_fragment_exit,R.anim.h_fragment_pop_enter,R.anim.h_fragment_pop_exit)
    }
}