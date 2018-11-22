package com.lovewandou.wd.functions.pwd

import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentFindPwdBinding
import com.lovewandou.wd.extension.showToast
import kotlinx.android.synthetic.main.fragment_find_pwd.*

/**
 * 描述:
 *
 * Created by and on 2018/11/20.
 */
class FindPwdFragment: WDFragment<FragmentFindPwdBinding>() {
    companion object {
            fun newInstance():FindPwdFragment = FindPwdFragment()
    }

    val vm = FindPwdVM()
    override fun getLayoutRes(): Int = R.layout.fragment_find_pwd

    override fun initView() {

        mBinding.vm = vm

        captcha_tv.setOnClickListener {
                    vm.send().safeSubscribeBy {
                        captcha_tv.start()
                    }
        }

        submit_tv.setOnClickListener {
            vm.submit().safeSubscribeBy {
                       context.showToast("密码重置成功")
                        pop()
                    }
        }

        back_btn.setOnClickListener {
            pop()
        }


    }
}