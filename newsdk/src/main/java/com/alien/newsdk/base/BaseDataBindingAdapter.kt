package com.alien.newsdk.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.Keep
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.alien.newsdk.BR
import com.alien.newsdk.R
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * 描述:
 *
 * Created by Alien on 2018/4/25.
 */
@Keep
open class BaseDataBindingAdapter<T,V:BaseDataBindingViewHolder<T>>(
        @LayoutRes itemLayoutRes:Int,
        data:List<T>?=null,
        open val emptyLayoutRes :Int?= null)
    : BaseQuickAdapter<T, V>(itemLayoutRes,data) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (getRecyclerView()==null) {
            bindToRecyclerView(recyclerView)
        }
    }
    override fun convert(helper: V, item: T) {
        helper.getDataBinding()?.apply {
            setVariable(BR.item,item)
            executePendingBindings()
            helper.onBind(data.indexOf(item),item)
        }

        getClickChildIds()?.forEach {
            helper.addOnClickListener(it)
        }
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
        return binding?.root?.apply {
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }?:super.getItemView(layoutResId, parent)
    }

    override fun setNewData(data: List<T>?) {
        super.setNewData(data)
        if (this@BaseDataBindingAdapter.data.isEmpty() && (data == null || data.isEmpty()) && emptyView == null && emptyLayoutRes !=null ){
            setEmptyView(emptyLayoutRes!!)
        }
    }

    override fun addData(newData: Collection<out T>) {
        super.addData(newData)
        if (data.isEmpty() && newData.isEmpty() && emptyView==null&&emptyLayoutRes!=null){
            setEmptyView(emptyLayoutRes!!)
        }
    }


    open fun getClickChildIds():Array<Int>?{
        return null
    }
}
