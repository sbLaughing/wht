package com.lovewht.wht.functions.find

import android.support.v7.widget.LinearLayoutManager
import com.alien.newsdk.base.CommonAdapter
import com.lovewht.wht.R
import com.lovewht.wht.base.WhtFragment
import com.lovewht.wht.databinding.FragmentSimpleListBinding
import kotlinx.android.synthetic.main.fragment_simple_list.*

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class SubFindFragment : WhtFragment<FragmentSimpleListBinding>() {

    companion object {
            fun newInstance():SubFindFragment = SubFindFragment()
    }

    val adapter = CommonAdapter<String>(R.layout.item_user_in_find)

    override fun getLayoutRes(): Int = R.layout.fragment_simple_list

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        adapter.setNewData(listOf("","","","","","","",""))

    }
}