package com.lovewandou.wd.functions.post

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import com.alien.newsdk.extensions.autoSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentPostDetailBinding
import com.lovewandou.wd.databinding.ItemHomeVideoBinding
import com.lovewandou.wd.functions.profile.UserProfileFragment
import com.lovewandou.wd.functions.video.BasePostViewHolder
import com.lovewandou.wd.functions.video.HomeVideoViewHolder
import com.lovewandou.wd.models.data.UserPostInfo
import kotlinx.android.synthetic.main.fragment_post_detail.*

/**
 * 描述:
 *
 * Created by and on 2018/11/5.
 */
class PostDetailFragment : WDFragment<FragmentPostDetailBinding>() {


    companion object {
            fun newInstance(postVM: PostVM):PostDetailFragment {
                val ret = PostDetailFragment()
                val b = Bundle()
                b.putParcelable("data",postVM.userPostInfo)
                ret.arguments = b
                return  ret
            }

            fun newInstance(postID:String):PostDetailFragment {
                val ret = PostDetailFragment()
                val b = Bundle()
                b.putString("id",postID)
                ret.arguments = b
                return  ret
            }
    }

    val viewmodel = PostDetailVM()
    val userPostInfo by lazy {
        arguments?.getParcelable<UserPostInfo>("data")
    }
    val postId by lazy {
        arguments?.getString("id")
    }

    var videoViewHolder:HomeVideoViewHolder?=null

    override fun getLayoutRes(): Int = R.layout.fragment_post_detail

    override fun initView() {
        if (userPostInfo ==null ){
            postId?.let {
                viewmodel.getPostDetail(it)
                        .autoSubscribeBy(this@PostDetailFragment) {
                            showData(it)
                        }
            }
        }else{
            userPostInfo?.let {userPostInfo->
                showData(userPostInfo)
            }
        }


    }


    fun showData(data:UserPostInfo){
        val vm = PostVM(data)
        vm.isExpanded = true

        val viewDataBinding = DataBindingUtil.inflate<ItemHomeVideoBinding>(LayoutInflater.from(context),R.layout.item_home_video,null,false)
        if (data.post_is_video){
            videoViewHolder = HomeVideoViewHolder(viewDataBinding.root)
            scroll_view.addView(videoViewHolder?.itemView)
            viewDataBinding.item = vm
            videoViewHolder?.onBind(0,vm)
            videoViewHolder?.setActive(null,0)
        }else{
            val imageViewHolder = BasePostViewHolder(viewDataBinding.root)
            scroll_view.addView(imageViewHolder.itemView)
            viewDataBinding.item = vm
            imageViewHolder.onBind(0,vm)
        }

        viewDataBinding.avatarImageview.setOnClickListener {
            val fragment = UserProfileFragment.newInstance(data)
            start(fragment)
        }
    }

    override fun onResume() {
        super.onResume()
        videoViewHolder?.onResume()
    }

    override fun onPause() {
        super.onPause()
        videoViewHolder?.onPause()
    }
}