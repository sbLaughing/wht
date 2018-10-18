package com.lovewht.wht.functions.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alien.newsdk.util.ScreenUtil
import com.lovewht.wht.R
import com.lovewht.wht.base.WhtFragment
import com.lovewht.wht.databinding.FragmentHomeBinding
import com.lovewht.wht.functions.share.ShareBottomDialog
import com.lovewht.wht.functions.video.VedioAdapter
import com.lovewht.wht.util.ScrollCalculatorHelper
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class HomeFragment : WhtFragment<FragmentHomeBinding>() {

    companion object {
            fun newInstance():HomeFragment = HomeFragment()
    }


    val adapter = object :VedioAdapter({
        it.onBind()
    }){
        override fun getClickChildIds(): Array<Int>? {
            return arrayOf(R.id.download_btn,R.id.share_btn)
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initView() {

        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.share_btn){
                ShareBottomDialog(_mActivity).show()

            }
        }

        //限定范围为屏幕一半的上下偏移180
        val playTop = ScreenUtil.getDisplayHeight(context) / 2 - ScreenUtil.dip2px( 180f)
        val playBottom = ScreenUtil.getDisplayHeight(context) / 2 + ScreenUtil.dip2px( 180f)
        //自定播放帮助类
        val scrollCalculatorHelper = ScrollCalculatorHelper(R.id.home_cover_vedio, playTop, playBottom)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            var firstVisibleItem: Int = 0
            var lastVisibleItem: Int = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                //这是滑动自动播放的代码
//                if (!mFull) {
                    scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem)
//                }
            }
        })


        val data = listOf("","","","","","","","","")
        adapter.setNewData(data)

    }

    override fun onBackPressedSupport(): Boolean {
        if (GSYVideoManager.backFromWindowFull(activity)){
            return true
        }
        return super.onBackPressedSupport()
    }

    override fun onPause() {
        GSYVideoManager.onPause()
        super.onPause()
    }

    override fun onResume() {
        GSYVideoManager.onResume()
        super.onResume()
    }


    override fun onDestroy() {
        GSYVideoManager.releaseAllVideos()
        super.onDestroy()
    }
}