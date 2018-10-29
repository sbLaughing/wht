package com.inwandou.wandou.base

import android.databinding.ViewDataBinding
import com.alien.newsdk.base.SDKFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
abstract class WDFragment<T:ViewDataBinding> : SDKFragment<T>() {


    fun getSupportParentFragment(): WDFragment<*>? {
        return parentFragment as? WDFragment<*>
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}