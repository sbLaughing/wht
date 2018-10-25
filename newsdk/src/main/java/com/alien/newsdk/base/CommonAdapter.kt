package com.alien.newsdk.base

import android.support.annotation.LayoutRes

/**
 * 描述:
 *
 * Created by and on 2018/10/16.
 */
open class CommonAdapter <T>(@LayoutRes itemLayoutRes:Int,
                        data:List<T>?=null,
                        override val emptyLayoutRes :Int?= null)
    : BaseDataBindingAdapter<T, BaseDataBindingViewHolder<T>>(
        itemLayoutRes, data, emptyLayoutRes
)