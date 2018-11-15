package com.lovewandou.wd.functions.video

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alien.newsdk.base.BaseDataBindingAdapter
import com.alien.newsdk.base.BaseDataBindingViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.lovewandou.wd.R
import com.waynell.videolist.visibility.items.ListItem
import com.waynell.videolist.visibility.scroll.ItemsProvider

/**
 * 描述:
 *
 * Created by and on 2018/11/6.
 */
class HomeMultiAdapter (val view: RecyclerView,val emptySearchCallback:()->Unit): BaseDataBindingAdapter<IMultiItem,BaseDataBindingViewHolder<IMultiItem>>(0,null),ItemsProvider {

    val emptyLayout :View
    init {

        multiTypeDelegate = object : MultiTypeDelegate<IMultiItem>(){
            override fun getItemType(t: IMultiItem): Int = t.itemType
        }

        multiTypeDelegate.registerItemType(0, R.layout.item_home)
        multiTypeDelegate.registerItemType(1, R.layout.item_home_video)

        emptyLayout = LayoutInflater.from(view.context).inflate(R.layout.layout_home_empty,null)
        emptyLayout.findViewById<View>(R.id.search_btn)
                .setOnClickListener {
                    emptySearchCallback()
                }
    }

    override fun getListItem(position: Int): ListItem? {
        val holder = view.findViewHolderForAdapterPosition(position)
        return if (holder is ListItem){
            holder
        }else{
            null
        }
    }

    override fun setNewData(data: List<IMultiItem>?) {
        super.setNewData(data)
        if (this@HomeMultiAdapter.data.isEmpty() && (data == null || data.isEmpty()) && emptyView == null){
            emptyView = emptyLayout
        }
    }

    override fun addData(newData: Collection<out IMultiItem>) {
        super.addData(newData)
        if (data.isEmpty() && newData.isEmpty() && emptyView==null){
            emptyView = emptyLayout
        }
    }

    override fun createBaseViewHolder(parent: ViewGroup?, layoutResId: Int): BaseDataBindingViewHolder<IMultiItem> {
        return when(layoutResId){
            R.layout.item_home->{
                return BasePostViewHolder(getItemView(layoutResId,parent)) as BaseDataBindingViewHolder<IMultiItem>
            }

            R.layout.item_home_video->{
                HomeVideoViewHolder(getItemView(layoutResId,parent)) as BaseDataBindingViewHolder<IMultiItem>
            }
            else->{
                super.createBaseViewHolder(parent, layoutResId)
            }
        }
    }

    override fun listItemSize(): Int = itemCount
}