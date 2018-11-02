package com.lovewandou.wd.functions.video

import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.alien.newsdk.base.BaseDataBindingViewHolder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lovewandou.wd.BuildConfig
import com.lovewandou.wd.R
import com.lovewandou.wd.functions.video.glide.GlideApp
import com.waynell.videolist.visibility.items.ListItem
import com.waynell.videolist.widget.TextureVideoView

/**
 * 描述:
 *
 * Created by and on 2018/10/24.
 */
class VideoViewHolder(view: View) : BaseDataBindingViewHolder<VideoViewModel>(view), VideoLoadMvpView, ListItem {


    companion object {
        private val STATE_IDLE = 0
        private val STATE_ACTIVED = 1
        private val STATE_DEACTIVED = 2

        private val TAG = "ListItem"
        private val SHOW_LOGS = BuildConfig.DEBUG
    }

    private var videoState = STATE_IDLE
    private var videoLocalPath: String? = null
    private var viewModel: VideoViewModel? = null

    private val vedioView: TextureVideoView = view.findViewById(R.id.video_view)
    private val coverView: ImageView = view.findViewById(R.id.video_cover)
    private var loadTarget: VideoLoadTarget = VideoLoadTarget(this)


    override fun onBind(position: Int, data: VideoViewModel) {
        super.onBind(position, data)
        viewModel = data

        vedioView.setOnClickListener {
            onClick()
        }
        reset()

//        GlideApp.with(itemView.context)
//                .load("http://img10.3lian.com/sc6/show02/67/27/03.jpg")
////                .load("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4")
//                .into(coverView)
        GlideApp.with(itemView.context)
                .asFile()
                .load(data.remotePath)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .downloadOnly(loadTarget)


//        GlideApp.with(itemView.context)
//                .asBitmap()
//                .load(data.remotePath)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
//                .into(coverView)

    }


    fun onClick() {
        if (videoState != STATE_ACTIVED) return

        if (videoView.isMute) {
            muteVideo(false)
        } else {
            muteVideo(true)
        }
    }

    fun muteVideo(isMute: Boolean) {
        if (isMute) {
            videoView.mute()
            viewModel?.isMute?.set(true)
        } else {
            videoView.unMute()
            viewModel?.isMute?.set(false)
        }
    }

    private fun reset() {
        videoState = STATE_IDLE
        videoView.stop()
        videoLocalPath = null
        videoStopped()
    }


    override fun getVideoView(): TextureVideoView {
        return vedioView
    }

    override fun videoBeginning() {
    }

    override fun videoStopped() {
    }

    override fun videoPrepared(player: MediaPlayer?) {
    }

    override fun videoResourceReady(videoPath: String?) {
        videoLocalPath = videoPath
        if (videoLocalPath != null) {
            videoView.setVideoPath(videoPath)
            if (videoState == STATE_ACTIVED) {
                videoView.start()
                muteVideo(true)
            }
        }
    }


    override fun setActive(newActiveView: View?, newActiveViewPosition: Int) {
        if (SHOW_LOGS) {
            Log.i(TAG, "setActive $newActiveViewPosition path $videoLocalPath")
        }

        videoState = STATE_ACTIVED
        if (videoLocalPath != null) {
            videoView.setVideoPath(videoLocalPath)
            videoView.start()
            muteVideo(true)
        }
    }

    override fun deactivate(currentView: View?, position: Int) {
        if (SHOW_LOGS) {
            Log.w(TAG, "deactivate $position")
        }

        videoState = STATE_DEACTIVED
        videoView.stop()
        videoStopped()
    }

    override fun onPause() {
        if (vedioView.isPlaying)
            vedioView.pause()
    }

    override fun onResume() {
        if (!vedioView.isPlaying)
            vedioView.resume()
    }


}