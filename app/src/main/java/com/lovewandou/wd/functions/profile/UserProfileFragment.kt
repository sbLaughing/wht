package com.lovewandou.wd.functions.profile

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.CommonAdapter
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentUserProfileBinding
import com.lovewandou.wd.functions.post.PostDetailFragment
import com.lovewandou.wd.functions.post.PostVM
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * 描述:
 *
 * Created by and on 2018/11/4.
 */
class UserProfileFragment : WDFragment<FragmentUserProfileBinding>() {

    companion object {

        fun newInstance(profileVM: ProfileVM): UserProfileFragment = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putParcelable("data", profileVM)
            }
        }


    }

    //    private val userinfo by lazy { arguments?.getParcelable<UserInfo>("data") }
    private val profileVM by lazy { arguments?.getParcelable<ProfileVM>("data") }

    private val mAdapter = CommonAdapter<PostVM>(R.layout.item_user_posts_in_list)

    override fun getLayoutRes(): Int = R.layout.fragment_user_profile

    override fun initView() {
        mBinding.vm = profileVM

        attend_tv.setOnClickListener {
            profileVM?.toggleAttend()?.safeSubscribeBy {
                profileVM?.notifyChange()
//                profileVM?.userInfo?.let {
//                    RxBus.get().post(UserAttendNotifyEvent(it))
//                }
            }
        }
        recycler_view.layoutManager = GridLayoutManager(context, 3)
        recycler_view.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mAdapter.getItem(position)?.let {
                start(PostDetailFragment.newInstance(it))
            }

        }
        profileVM?.getUserPosts(profileVM?.userInfo?.user_id ?: "")
                ?.safeSubscribeBy {
                    mAdapter.setNewData(it.map { PostVM(it) })
                }

    }

}

