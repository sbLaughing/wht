package com.inwandou.wandou.functions.find

import android.support.v7.widget.LinearLayoutManager
import com.alien.newsdk.base.CommonAdapter
import com.inwandou.wandou.R
import com.inwandou.wandou.base.WDFragment
import com.inwandou.wandou.databinding.FragmentSimpleListBinding
import kotlinx.android.synthetic.main.fragment_simple_list.*

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class SubFindFragment : WDFragment<FragmentSimpleListBinding>() {

    companion object {
            fun newInstance():SubFindFragment = SubFindFragment()
    }

    val adapter = CommonAdapter<String>(R.layout.item_user_in_find)

    override fun getLayoutRes(): Int = R.layout.fragment_simple_list

    override fun initView() {
        setSwipeBackEnable(false)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        adapter.setNewData(listOf("","","","","","","",""))

    }
}