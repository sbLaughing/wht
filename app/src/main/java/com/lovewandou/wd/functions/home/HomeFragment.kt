package com.lovewandou.wd.functions.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentHomeBinding
import com.lovewandou.wd.extension.handleSkipLoadmore
import com.lovewandou.wd.functions.post.PostVM
import com.lovewandou.wd.functions.profile.UserProfileFragment
import com.lovewandou.wd.functions.search.SearchFragment
import com.lovewandou.wd.functions.video.HomeMultiAdapter
import com.lovewandou.wd.models.AppData
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
        fun newInstance(): HomeFragment = HomeFragment()
    }


    private var mScrollState: Int = 0
    lateinit var mAdapter: HomeMultiAdapter
    lateinit var mCalculator: SingleListViewItemActiveCalculator
    val homeVM = HomeVM()

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initView() {
        setSwipeBackEnable(false)

        mAdapter = HomeMultiAdapter(recycler_view){
            getSupportParentFragment()?.extraTransaction()?.startDontHideSelf(SearchFragment.newInstance())
        }
        val layoutManager = LinearLayoutManager(context)

        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            (mAdapter.getItem(position) as? PostVM)?.let {
                val fragment = UserProfileFragment.newInstance(it.userPostInfo)
                getSupportParentFragment()?.start(fragment)
            }
        }


        mAdapter.setOnLoadMoreListener({
            loadData(isLoadmore = true)
        },recycler_view)

        swipe_refresh_layout.setOnRefreshListener {
            loadData(isLoadmore = false)
        }


        mCalculator = SingleListViewItemActiveCalculator(mAdapter, RecyclerViewItemPositionGetter(layoutManager, recycler_view))
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mScrollState = newState
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.itemCount > 0) {
                    mCalculator.onScrollStateIdle()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mCalculator.onScrolled(mScrollState)
            }
        })

        if (!AppData.appVM.isLogin){
            mAdapter.setNewData(listOf())
        }else{
            loadData(isLoadmore = false)
        }

    }

    override fun loadData(isLoadmore: Boolean) {
        if (!isLoadmore) homeVM.skip = 0
        homeVM.getHomeFeed()
                .doOnSubscribe {
                    if (!isLoadmore) swipe_refresh_layout.isRefreshing = true
                }
                .doAfterTerminate {
                    swipe_refresh_layout.isRefreshing = false
                }
                .handleSkipLoadmore(mAdapter,homeVM)
                .safeSubscribeBy {
                    if (!isLoadmore){
                        mAdapter.setNewData(it.map { PostVM(it) })
                    }else{
                        mAdapter.addData(it.map { PostVM(it) })
                    }
                }
    }

//    override fun onSupportVisible() {
//        super.onSupportVisible()
//        mCalculator.currentItem?.listItem?.onResume()
//    }
//
//    override fun onSupportInvisible() {
//        super.onSupportInvisible()
//        mCalculator.currentItem?.listItem?.onPause()
//    }

}