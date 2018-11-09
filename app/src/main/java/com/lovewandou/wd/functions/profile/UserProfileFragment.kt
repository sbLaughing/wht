package com.lovewandou.wd.functions.profile

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.alien.newsdk.extensions.dp2px
import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.CommonAdapter
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.common.GridDecoration
import com.lovewandou.wd.databinding.FragmentUserProfileBinding
import com.lovewandou.wd.extension.handleSkipLoadmore
import com.lovewandou.wd.functions.post.PostDetailFragment
import com.lovewandou.wd.functions.post.PostVM
import com.lovewandou.wd.models.data.UserPostInfo
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * 描述:
 *
 * Created by and on 2018/11/4.
 */
class UserProfileFragment : WDFragment<FragmentUserProfileBinding>() {

    companion object {

        fun newInstance(profileVM: ProfileVM): UserProfileFragment {
            val ret = UserProfileFragment()
            val b = Bundle()
            b.putParcelable("data", profileVM)
            ret.arguments = b
            return ret
        }


        fun newInstance(userPostInfo: UserPostInfo): UserProfileFragment =
                newInstance(ProfileVM(userInfo = userPostInfo.toUserInfo()))
    }

    private val profileVM by lazy { arguments?.getParcelable<ProfileVM>("data") }

    private val mAdapter = CommonAdapter<PostVM>(R.layout.item_user_posts_in_list)

    override fun getLayoutRes(): Int = R.layout.fragment_user_profile

    override fun initView() {

        profileVM?.let { vm ->
            mBinding.vm = vm

            attend_tv.setOnClickListener {
                vm.toggleAttend()?.safeSubscribeBy {
                    vm.notifyChange()
                }
            }
            recycler_view.layoutManager = GridLayoutManager(context, 3)
            recycler_view.adapter = mAdapter
            recycler_view.addItemDecoration(GridDecoration(1.dp2px()))

            mAdapter.setOnItemClickListener { adapter, view, position ->
                mAdapter.getItem(position)?.let {
                    start(PostDetailFragment.newInstance(it))
                }
            }

            mAdapter.setEnableLoadMore(true)
            mAdapter.setOnLoadMoreListener({
                vm.getUserPosts(vm.userInfo.user_id)
                        .handleSkipLoadmore(mAdapter,vm)
                        .safeSubscribeBy {
                            mAdapter.addData(it.map { PostVM(it) })
                        }
            }, recycler_view)


            vm.getUserPosts(vm.userInfo.user_id)
                    .handleSkipLoadmore(mAdapter,vm)
                    .safeSubscribeBy {
                        mAdapter.setNewData(it.map { PostVM(it) })
                    }

            if (!vm.userInfo.isAttendValid()) {
                vm.fetchUserInfo().safeSubscribeBy { }
            }
        }


    }

}

