package com.lovewandou.wd.functions.login

import android.app.Activity
import android.os.Bundle
import com.alien.newsdk.extensions.autoSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentLoginSelectBinding
import com.lovewandou.wd.extension.showToast
import com.lovewandou.wd.functions.main.MainActivity
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
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

    val loginVM = LoginVM()

    override fun initView() {
        close_btn.setOnClickListener {
            pop()
        }

        account_login_tv.setOnClickListener {
            startForResult(LoginFragment.newInstance(),1)
        }

        weibo_login_tv.setOnClickListener {
            (_mActivity as? MainActivity)?.mSsoHandler?.authorize(object :WbAuthListener{
                override fun onSuccess(p0: Oauth2AccessToken) {
                    loginVM.getWeiboUserInfo(p0.token,p0.uid)
                            .autoSubscribeBy (this@LoginSelectFragment){
                                context?.showToast("微博登录成功")
//                                popTo(MainFragment::class.java,false)
                                pop()
                            }
                }

                override fun onFailure(p0: WbConnectErrorMessage?) {
                    context?.showToast("微博登录失败")
                }

                override fun cancel() {
                    context?.showToast("微博登录取消")
                }
            })
        }
    }


    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK) pop()
    }



    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return FragmentAnimator(R.anim.v_fragment_enter,R.anim.v_fragment_exit,R.anim.h_fragment_pop_enter,R.anim.h_fragment_pop_exit)
    }
}