package com.lovewandou.wd.functions.search

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.CommonAdapter
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentSearchBinding
import com.lovewandou.wd.extension.showToast
import com.lovewandou.wd.functions.find.FindVM
import com.lovewandou.wd.functions.profile.ProfileVM
import com.lovewandou.wd.functions.profile.UserProfileFragment
import com.lovewandou.wd.models.data.SearchWrapper
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
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
    val mAdapter = object : CommonAdapter<ProfileVM>(R.layout.item_my_attend,emptyLayoutRes = null) {
        override fun getClickChildIds(): Array<Int>? {
            return arrayOf(R.id.attend_tv)
        }
    }

    val flow: FlowableProcessor<String> = PublishProcessor.create<String>().toSerialized()

    override fun getLayoutRes(): Int = R.layout.fragment_search

    override fun initView() {

        setSwipeBackEnable(false)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            mAdapter.getItem(position)?.let {
                start(UserProfileFragment.newInstance(it))
            }
        }
        search_bar.editText.requestFocus()
        cancel_btn.setOnClickListener {
            pop()
        }
        showSoftInput(search_bar.editText)


        search_bar.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    flow.onNext("")
                    mAdapter.setNewData(listOf())
                    empty_layout.visibility = View.GONE
                }else{
                    flow.onNext(s.toString())
                }
            }
        })

        flow.debounce(500, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .flatMapMaybe {key->
                        return@flatMapMaybe findVM.searchUsers(key = key)
                                .map {
                                    SearchWrapper(key,it)
                                }
                }
                .safeSubscribeBy {
                    mAdapter.setNewData(it.result.map { ProfileVM(it) })
                    if (it.result.isEmpty()){
                        empty_layout.visibility = View.VISIBLE
                        var string = String.format(getString(R.string.search_empty_hint,it.key,it.key))
                        empty_tv.text =  Html.fromHtml(string)
                    }else{
                        empty_layout.visibility = View.GONE
                    }
                }

        auto_attend_tv.setOnClickListener { _ ->
            findVM.autoAttend(search_bar.searchContent)
                    .safeSubscribeBy {
                        context?.showToast("自动关注成功")
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