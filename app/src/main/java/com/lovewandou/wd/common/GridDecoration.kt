package com.lovewandou.wd.common

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 描述:
 *
 * Created by and on 2018/11/7.
 */
class GridDecoration(val singleDistance: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        super.getItemOffsets(outRect, view, parent, state)
        (parent.layoutManager as? GridLayoutManager)?.apply {
            val position = this.getPosition(view)
            outRect.set(singleDistance, singleDistance, singleDistance, 2 * singleDistance)
            if (position % spanCount == 0) {//第一列
                outRect.left = 0
                outRect.right = singleDistance * 2
            } else if (position % spanCount == spanCount - 1) {//最后一列
                outRect.right = 0
                outRect.left = singleDistance * 2
            }

            if (position < spanCount) {
                outRect.top = 0
            }


        }
    }

}