package com.lovewandou.wd.extension

import com.alien.newsdk.base.BaseDataBindingAdapter
import com.lovewandou.wd.base.BaseSkipVM
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/11/7.
 */

fun <T : List<*>> Maybe<T>.handleSkipLoadmore(mAdapter: BaseDataBindingAdapter<*, *>, vm: BaseSkipVM): Maybe<T> {
    return this.doOnSuccess {
        if (it.size < vm.limit) mAdapter.loadMoreEnd(true)
        else {
            mAdapter.loadMoreComplete()
        }
        vm.skip+=vm.limit
    }.doOnError {
        mAdapter.loadMoreFail()
    }
}