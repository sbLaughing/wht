package com.lovewht.wht.functions.find

import com.lovewht.wht.R
import com.lovewht.wht.base.WhtFragment
import com.lovewht.wht.databinding.FragmentFindBinding
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * 描述:
 *
 * Created by and on 2018/10/14.
 */
class FindFragment : WhtFragment<FragmentFindBinding>() {


    companion object {
        val tabs = listOf("推荐","网红","明星","动物","旅游","商业")

        fun newInstance():FindFragment = FindFragment()
    }


    override fun initView() {


        tabs.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it))
        }

    }

    override fun getLayoutRes(): Int = R.layout.fragment_find
}