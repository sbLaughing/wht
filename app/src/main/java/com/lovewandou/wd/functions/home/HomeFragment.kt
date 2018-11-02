package com.lovewandou.wd.functions.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentHomeBinding
import com.lovewandou.wd.functions.share.ShareBottomDialog
import com.lovewandou.wd.functions.video.VedioAdapter
import com.lovewandou.wd.functions.video.VideoViewModel
import com.waynell.videolist.visibility.calculator.SingleListViewItemActiveCalculator
import com.waynell.videolist.visibility.scroll.RecyclerViewItemPositionGetter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 描述:
 *
 * Created by and on 2018/10/13.
 */
class HomeFragment : WDFragment<FragmentHomeBinding>() {

    companion object {
            fun newInstance():HomeFragment = HomeFragment()
    }


    private var mScrollState: Int = 0
    lateinit var adapter :VedioAdapter
    lateinit var mCalculator: SingleListViewItemActiveCalculator

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initView() {
        setSwipeBackEnable(false)
        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager

        adapter = object :VedioAdapter(recycler_view){
            override fun getClickChildIds(): Array<Int>? {
                return arrayOf(R.id.download_btn,R.id.share_btn)
            }
        }
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.share_btn){
                ShareBottomDialog(_mActivity).show()
            }
        }
        recycler_view.adapter = adapter


        mCalculator = SingleListViewItemActiveCalculator(adapter,RecyclerViewItemPositionGetter(layoutManager,recycler_view))
        recycler_view.addOnScrollListener(object :RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                mScrollState = newState
                if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.itemCount > 0) {
                    mCalculator.onScrollStateIdle()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                mCalculator.onScrolled(mScrollState)
            }
        })



        val data = listOf(
                VideoViewModel("https://media.w3.org/2010/05/sintel/trailer.mp4"),

                VideoViewModel("http://www.w3school.com.cn/example/html5/mov_bbb.mp4"),

                VideoViewModel("https://www.w3schools.com/html/movie.mp4"),

                VideoViewModel("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"),
                VideoViewModel("http://techslides.com/demos/sample-videos/small.mp4"),
                VideoViewModel("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"),
                VideoViewModel("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapter.setNewData(data)

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (mCalculator.currentItem!=null){
            mCalculator.currentItem.listItem.onResume()
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        if (mCalculator.currentItem!=null){
            mCalculator.currentItem.listItem.onPause()
        }
    }

}