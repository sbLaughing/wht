package com.alien.newsdk.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import me.yokeyword.fragmentation.SupportActivity

/**
 * Created by Alien on 2017/12/7.
 */
abstract class SDKActivity<VB : ViewDataBinding> : SupportActivity(){

    protected lateinit var mBinding: VB
    protected lateinit var mContext: Context

    private var rootViewVisibleHeight: Int = 0
    var mRootView: View?=null

    var mLoadingHandler: Handler?=null
        private set

    abstract fun initView()
    abstract fun getLayoutRes(): Int

    open fun statusBarBlack(): Boolean {
        return false
    }

    open fun statusBarBlackResult(result: Int) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        mContext = this

//        if (statusBarBlack()) {
//            val result = StatusBarUtil.StatusBarLightMode(this)
//            statusBarBlackResult(result)
//        }
        initView()
    }


    protected fun getRootView(): View? {
        if (mRootView == null)
            mRootView = window.decorView.findViewById(android.R.id.content)
        return mRootView
    }


    /**
     * 当有键盘弹出时自动收缩布局
     *
     * @param container
     */
    protected fun registerAutoCollaspe(container: ViewGroup) {
        val decorView = window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            val viewLp = container.layoutParams
            viewLp.height = rect.bottom
            container.requestLayout()
        }
    }

    protected fun listenKeyBoradStatus() {
        val decorView = window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            val visibleHeight = rect.height()

            if (rootViewVisibleHeight == 0 || rootViewVisibleHeight == visibleHeight) {
                rootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            }

            if (rootViewVisibleHeight - visibleHeight > 200) {
                onKeyboardStatusChanged(true)
                rootViewVisibleHeight = visibleHeight
            } else if (visibleHeight - rootViewVisibleHeight > 200) {
                onKeyboardStatusChanged(false)
                rootViewVisibleHeight = visibleHeight

            }
        })
    }

    open fun onKeyboardStatusChanged(isKeyboardVisible: Boolean) {

    }

}