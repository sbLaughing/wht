package com.lovewandou.wd.functions.find

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.CommonAdapter
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentSimpleListBinding
import com.lovewandou.wd.functions.profile.ProfileVM
import com.lovewandou.wd.functions.profile.UserProfileFragment
import kotlinx.android.synthetic.main.fragment_simple_list.*

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class SubFindFragment : WDFragment<FragmentSimpleListBinding>() {

    companion object {
            fun newInstance(labelTag:String):SubFindFragment = SubFindFragment().apply {
                arguments = Bundle()
                arguments?.putString("tag",labelTag)
            }
    }

    val findVm=FindVM()
    val tagLabel by lazy { arguments?.getString("tag")?:"" }
    private val mAdapter = object : CommonAdapter<ProfileVM>(R.layout.item_user_in_find){
        override fun getClickChildIds(): Array<Int>? {
            return arrayOf(R.id.attend_tv)
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_simple_list

    override fun initView() {
        setSwipeBackEnable(false)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter

        swipe_refresh_layout.setOnRefreshListener {
            loadData()
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mAdapter.getItem(position)?.let {
                getSupportParentFragment()?.getSupportParentFragment()?.start(UserProfileFragment.newInstance(it))
            }

        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            mAdapter.getItem(position)?.apply {
                toggleAttend().safeSubscribeBy {  }
            }
        }


        loadData()

    }

    override fun loadData(){
        findVm.searchUsers(tag = tagLabel)
                .doOnSubscribe {
                    swipe_refresh_layout.isRefreshing = true
                }
                .doAfterTerminate {
                    swipe_refresh_layout.isRefreshing = false
                }
                .doOnError {
                    it.printStackTrace()
                }
                .safeSubscribeBy {
                    mAdapter.setNewData(it.map { ProfileVM(it) })
                }
    }

}