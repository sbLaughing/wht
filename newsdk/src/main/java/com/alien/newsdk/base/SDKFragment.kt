package com.alien.newsdk.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment

abstract class SDKFragment<VB : ViewDataBinding> : SwipeBackFragment(){
//    var baseActivity: SDKActivity<*>? = null

    protected lateinit var mBinding: VB

//    val mContext: Context
//        @JvmName("getContext2")
//        get() = context!!
//
//    val mActivity: Activity
//        @JvmName("getActivity2")
//        get() = activity!!


    abstract fun getLayoutRes(): Int
    abstract fun initView()
    fun loadData(){}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), null, false)
        return attachToSwipeBack(mBinding.root)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }



//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        if (context is BaseActivity<*>) {
//            baseActivity = context
//        }
//    }
}