package com.lovewandou.wd.functions.video

import android.view.View
import android.widget.ImageView
import com.alien.newsdk.base.BaseDataBindingViewHolder
import com.lovewandou.wd.R
import com.lovewandou.wd.functions.post.PostVM

/**
 * 描述:
 *
 * Created by and on 2018/11/6.
 */
class HomeImageViewHolder(view: View): BaseDataBindingViewHolder<PostVM>(view){
    private val coverView: ImageView = view.findViewById(R.id.video_cover)


    override fun onBind(position: Int, data: PostVM) {
        super.onBind(position, data)
    }

}