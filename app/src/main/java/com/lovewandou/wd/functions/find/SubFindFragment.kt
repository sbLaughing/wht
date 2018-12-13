package com.lovewandou.wd.functions.find

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alien.newsdk.extensions.autoSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.CommonAdapter
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentSimpleListBinding
import com.lovewandou.wd.extension.handleSkipLoadmore
import com.lovewandou.wd.functions.login.LoginSelectFragment
import com.lovewandou.wd.functions.post.PostDetailFragment
import com.lovewandou.wd.functions.post.PostVM
import com.lovewandou.wd.functions.profile.ProfileVM
import com.lovewandou.wd.functions.profile.UserProfileFragment
import com.lovewandou.wd.models.AppData
import kotlinx.android.synthetic.main.fragment_simple_list.*

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class SubFindFragment : WDFragment<FragmentSimpleListBinding>() {

    companion object {
        fun newInstance(labelTag: String): SubFindFragment = SubFindFragment().apply {
            arguments = Bundle()
            arguments?.putString("tag", labelTag)
        }
    }

    val findVm = FindVM()
    val tagLabel by lazy { arguments?.getString("tag") ?: "" }
    private val mAdapter = object : CommonAdapter<ProfileVM>(R.layout.item_user_in_find) {
        override fun getClickChildIds(): Array<Int>? {
            return arrayOf(R.id.attend_tv,R.id.image0,R.id.image0,R.id.image1,R.id.image2)
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_simple_list

    override fun initView() {
        setSwipeBackEnable(false)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter

        swipe_refresh_layout.setOnRefreshListener {
            getData()
        }

        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({
            getData(true)
        }, recycler_view)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mAdapter.getItem(position)?.let {
                getSupportParentFragment()?.getSupportParentFragment()?.start(UserProfileFragment.newInstance(it))
            }

        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.attend_tv->{
                    mAdapter.getItem(position)?.apply {
                        if (AppData.appVM.isLogin) {
                            toggleAttend().autoSubscribeBy(this@SubFindFragment) { }
                        }
                        else{
                            getSupportParentFragment()?.getSupportParentFragment()?.extraTransaction()?.startDontHideSelf(LoginSelectFragment.newInstance())
                        }
                    }
                }
                R.id.image0->{
                    mAdapter.getItem(position)?.userInfo?.getPreviewImage0()?.let {
                        getSupportParentFragment()?.getSupportParentFragment()?.start(PostDetailFragment.newInstance(PostVM(it)))
                    }

                }
                R.id.image1->{
                    mAdapter.getItem(position)?.userInfo?.getPreviewImage1()?.let {
                        getSupportParentFragment()?.getSupportParentFragment()?.start(PostDetailFragment.newInstance(PostVM(it)))
                    }
                }
                R.id.image2->{
                    mAdapter.getItem(position)?.userInfo?.getPreviewImage2()?.let {
                        getSupportParentFragment()?.getSupportParentFragment()?.start(PostDetailFragment.newInstance(PostVM(it)))
                    }
                }
            }

        }


        getData()

    }

    fun getData(isLoadmore: Boolean = false) {
        if (!isLoadmore) findVm.skip = 0
        val maybe = findVm.searchUsers(tag = tagLabel)
        if (isLoadmore) {
            maybe
        } else {
            maybe.doOnSubscribe {
                swipe_refresh_layout?.isRefreshing = true
            }.doAfterTerminate {
                swipe_refresh_layout?.isRefreshing = false
            }
        }
                .handleSkipLoadmore(mAdapter, findVm)
                .autoSubscribeBy (this@SubFindFragment){
                    if (findVm.firstPage()){
                        mAdapter.setNewData(it.map { ProfileVM(it) })
                    }
                    else mAdapter.addData(it.map { ProfileVM(it) })
                }
    }

}