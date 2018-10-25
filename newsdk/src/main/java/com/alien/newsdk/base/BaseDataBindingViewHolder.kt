package com.alien.newsdk.base

import android.databinding.ViewDataBinding
import android.support.annotation.Keep
import android.view.View
import com.alien.newsdk.R
import com.chad.library.adapter.base.BaseViewHolder

/**
 * 描述:
 *
 * Created by Alien on 2018/4/25.
 */
@Keep
open class BaseDataBindingViewHolder<T>(view:View) : BaseViewHolder(view) {

    fun getDataBinding(): ViewDataBinding? {
        return itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding?
    }

    open fun onBind(position:Int,data:T){

    }
}