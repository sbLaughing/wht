package com.lovewandou.wd.base

import android.support.annotation.LayoutRes
import com.alien.newsdk.base.BaseDataBindingAdapter
import com.alien.newsdk.base.BaseDataBindingViewHolder
import com.lovewandou.wd.R

/**
 * 描述:
 *
 * Created by and on 2018/10/16.
 */
open class CommonAdapter <T>(@LayoutRes itemLayoutRes:Int,
                        data:List<T>?=null,
                        override val emptyLayoutRes :Int?= R.layout.layout_list_empty)
    : BaseDataBindingAdapter<T, BaseDataBindingViewHolder<T>>(
        itemLayoutRes, data, emptyLayoutRes
)