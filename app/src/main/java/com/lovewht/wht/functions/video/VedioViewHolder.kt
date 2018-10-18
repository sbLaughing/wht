package com.lovewht.wht.functions.video

import android.view.View
import com.alien.newsdk.base.BaseDataBindingViewHolder
import com.lovewht.wht.R
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack

/**
 * 描述:
 *
 * Created by and on 2018/10/16.
 */
class VedioViewHolder (view:View): BaseDataBindingViewHolder(view) {

    lateinit var builder : GSYVideoOptionBuilder
    lateinit var homeCoverVedio: HomeCoverVedio

    init {
        builder = GSYVideoOptionBuilder()
        homeCoverVedio = view.findViewById(R.id.home_cover_vedio)
    }

    fun onBind(){

        val url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        val title = "标题"
        builder.setIsTouchWiget(false)
                .setUrl(url)
                .setSetUpLazy(true)//lazy可以防止滑动卡顿
                .setVideoTitle(title)
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setPlayTag("VedioViewHolder")
                .setLockLand(true)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setIsTouchWigetFull(false)
                .setPlayPosition(position)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any) {
                        super.onPrepared(url, *objects)
                        if (!homeCoverVedio.isIfCurrentIsFullscreen) {
                            //静音
                            GSYVideoManager.instance().isNeedMute = true
                        }

                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                        super.onQuitFullscreen(url, *objects)
                        //全屏不静音
                        GSYVideoManager.instance().isNeedMute = true
                    }

                    override fun onEnterFullscreen(url: String?, vararg objects: Any) {
                        super.onEnterFullscreen(url, *objects)
                        GSYVideoManager.instance().isNeedMute = false
                        homeCoverVedio.currentPlayer.titleTextView.text = objects[0] as String
                    }
                }).build(homeCoverVedio)

        homeCoverVedio.backButton.visibility = View.GONE
        homeCoverVedio.titleTextView.visibility = View.GONE
        homeCoverVedio.fullscreenButton.visibility = View.GONE

        itemView.findViewById<ExpandableTextView>(R.id.expandable_tv_layout)
                .text = "如果是在自己项目中新建了module（比如core）作为主module（app）的依赖module，需要更改依赖module（core）的gradle中的“apply plugin: ‘com.android.application’”->”apply plugin: ‘com.android.library’”,并且注释掉applicationId行。\n" +
                "--------------------- \n" +
                "作者：灭谛 \n" +
                "来源：CSDN \n" +
                "原文：https://blog.csdn.net/u010296640/article/details/79114199 \n" +
                "版权声明：本文为博主原创文章，转载请附上博文链接！如果是在自己项目中新建了module（比如core）作为主module（app）的依赖module，需要更改依赖module（core）的gradle中的“apply plugin: ‘com.android.application’”->”apply plugin: ‘com.android.library’”,并且注释掉applicationId行。\n" +
                "--------------------- \n" +
                "作者：灭谛 \n" +
                "来源：CSDN \n" +
                "原文：https://blog.csdn.net/u010296640/article/details/79114199 \n" +
                "版权声明：本文为博主原创文章，转载请附上博文链接！"
    }
}