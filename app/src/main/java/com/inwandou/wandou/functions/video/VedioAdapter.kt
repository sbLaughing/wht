package com.inwandou.wandou.functions.video

import android.support.v7.widget.RecyclerView
import com.alien.newsdk.base.BaseDataBindingAdapter
import com.inwandou.wandou.R
import com.waynell.videolist.visibility.items.ListItem
import com.waynell.videolist.visibility.scroll.ItemsProvider

/**
 * 描述:
 *
 * Created by and on 2018/10/16.
 */
open class VedioAdapter(var view: RecyclerView)
    : BaseDataBindingAdapter<VideoViewModel,VideoViewHolder>(R.layout.item_home_video, listOf(),null),ItemsProvider {
    override fun getListItem(position: Int): ListItem? {
        val holder = view.findViewHolderForAdapterPosition(position)
        return if (holder is ListItem) {
            holder
        } else null
    }

    override fun listItemSize(): Int  = itemCount
}