package com.lovewandou.wd.functions.search

import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.View
import com.alien.newsdk.extensions.autoSubscribeBy
import com.alien.newsdk.network.async
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lovewandou.wd.R
import com.lovewandou.wd.base.CommonAdapter
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentSearchBinding
import com.lovewandou.wd.extension.handleSkipLoadmore
import com.lovewandou.wd.extension.showToast
import com.lovewandou.wd.functions.find.FindVM
import com.lovewandou.wd.functions.profile.ProfileVM
import com.lovewandou.wd.functions.profile.UserProfileFragment
import com.lovewandou.wd.models.data.SearchWrapper
import kotlinx.android.synthetic.main.fragment_search.*
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.concurrent.TimeUnit

/**
 * 描述:
 *
 * Created by and on 2018/11/8.
 */
class SearchFragment : WDFragment<FragmentSearchBinding>() {
    companion object {
        fun newInstance(): SearchFragment = SearchFragment()
    }

    val findVM = FindVM()
    val mAdapter = object : CommonAdapter<ProfileVM>(R.layout.item_my_attend, emptyLayoutRes = null) {
        override fun getClickChildIds(): Array<Int>? {
            return arrayOf(R.id.attend_tv)
        }
    }


    override fun getLayoutRes(): Int = R.layout.fragment_search

    override fun initView() {

        setSwipeBackEnable(false)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position)?.let {
                start(UserProfileFragment.newInstance(it))
            }
        }

        mAdapter.setOnLoadMoreListener({
            search()
        }, recycler_view)
        search_bar.editText.requestFocus()
        cancel_btn.setOnClickListener {
            pop()
        }
        showSoftInput(search_bar.editText)

        RxTextView.textChanges(search_bar.editText)
                .debounce(150,TimeUnit.MILLISECONDS)
                .async()
                .autoSubscribeBy(this@SearchFragment) {
                    if (it.isNullOrEmpty()){
                        mAdapter.setNewData(listOf())
                        empty_layout.visibility = View.GONE
                    }else{
                        findVM.currentKey = it.toString()
                        findVM.resetPaging()
                        search()
                    }
                }

        auto_attend_tv.setOnClickListener {
            findVM.autoAttend(search_bar.searchContent)
                    .autoSubscribeBy(this@SearchFragment) {
                        context?.showToast("自动关注成功")
                    }
        }
    }

    fun search() {
        findVM.searchUsers()
                .handleSkipLoadmore(mAdapter, findVM)
                .map {
                    SearchWrapper(findVM.currentKey ?: "", it)
                }
                .autoSubscribeBy(this@SearchFragment) {
                    if (findVM.firstPage()) {
                        mAdapter.setNewData(it.result.map { ProfileVM(it, showAttendBtn = false) })
                        if (it.result.isEmpty()) {
                            empty_layout.visibility = View.VISIBLE
                            var string = String.format(getString(R.string.search_empty_hint, it.key, it.key))
                            empty_tv.text = Html.fromHtml(string)
                        } else {
                            empty_layout.visibility = View.GONE
                        }
                    } else {
                        mAdapter.addData(it.result.map { ProfileVM(it, showAttendBtn = false) })
                    }
                }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return FragmentAnimator(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.h_fragment_pop_enter, R.anim.h_fragment_pop_exit)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        search_bar.editText.clearFocus()
        hideSoftInput()
    }
}