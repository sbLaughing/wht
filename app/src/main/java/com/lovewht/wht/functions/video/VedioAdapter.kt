package com.lovewht.wht.functions.video

import com.alien.newsdk.base.BaseDataBindingAdapter
import com.lovewht.wht.R

/**
 * 描述:
 *
 * Created by and on 2018/10/16.
 */
open class VedioAdapter(override val onBinding: (VedioViewHolder) -> Unit)
    : BaseDataBindingAdapter<String,VedioViewHolder>(R.layout.item_home_video, listOf(),null,onBinding)