package com.lovewandou.wd.functions.post

import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentPostDetailBinding

/**
 * 描述:
 *
 * Created by and on 2018/11/5.
 */
class PostDetailFragment : WDFragment<FragmentPostDetailBinding>() {


    companion object {
            fun newInstance(postVM: PostVM):PostDetailFragment = PostDetailFragment()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_post_detail

    override fun initView() {
    }
}