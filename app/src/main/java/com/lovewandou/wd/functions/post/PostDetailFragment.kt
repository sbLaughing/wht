package com.lovewandou.wd.functions.post

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentPostDetailBinding
import com.lovewandou.wd.databinding.ItemHomeBinding
import com.lovewandou.wd.databinding.ItemHomeVideoBinding
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
    }

    val userPostInfo by lazy {
        arguments?.getParcelable<UserPostInfo>("data")
    }

    var videoViewHolder:HomeVideoViewHolder?=null

    override fun getLayoutRes(): Int = R.layout.fragment_post_detail

    override fun initView() {
        userPostInfo?.let {
            val vm = PostVM(it)
            if (it.post_is_video){
                val viewDataBinding = DataBindingUtil.inflate<ItemHomeVideoBinding>(LayoutInflater.from(context),R.layout.item_home_video,null,false)
                videoViewHolder = HomeVideoViewHolder(viewDataBinding.root)
                scroll_view.addView(videoViewHolder?.itemView)
                viewDataBinding.item = vm
                videoViewHolder?.onBind(0,vm)
                videoViewHolder?.setActive(null,0)
            }else{
                val viewDatabinding= DataBindingUtil.inflate<ItemHomeBinding>(LayoutInflater.from(context),R.layout.item_home,null,false)
                scroll_view.addView(viewDatabinding.root)
                viewDatabinding.item = vm
            }
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