package com.lovewandou.wd.functions.video

import android.media.MediaPlayer
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.alien.newsdk.extensions.saveGalleryAsVideo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.transition.Transition
import com.lovewandou.wd.BuildConfig
import com.lovewandou.wd.R
import com.lovewandou.wd.extension.showToast
import com.lovewandou.wd.functions.post.PostVM
import com.waynell.videolist.visibility.items.ListItem
import com.waynell.videolist.widget.ScaleType
import com.waynell.videolist.widget.TextureVideoView
import java.io.File


/**
 * 描述:
 *
 * Created by and on 2018/10/24.
 */
class HomeVideoViewHolder(view: View, override val adapter: HomeMultiAdapter? = null) : BasePostViewHolder(view, adapter), VideoLoadMvpView, ListItem, ViewPropertyAnimatorListener {


    companion object {
        private val STATE_IDLE = 0
        private val STATE_ACTIVED = 1
        private val STATE_DEACTIVED = 2

        private val TAG = "ListItem"
        private val SHOW_LOGS = BuildConfig.DEBUG
    }

    private var videoState = STATE_IDLE
    private var videoLocalPath: String? = null
    private var viewModel: PostVM? = null

    private val vedioView: TextureVideoView = view.findViewById(R.id.video_view)
    private val coverView: ImageView = view.findViewById(R.id.video_cover)
    private var loadTarget: VideoLoadTarget = VideoLoadTarget(this)//下载好了回调播放回调


    override fun onVideoDownload(view: View, url: String) {
        Glide.with(view.context)
                .asFile()
                .load(url)
                .into(ImageView(view.context))

        Glide.with(view.context)
                .asFile()
                .load(url)
                .into(object : BaseTarget<File>() {
                    override fun getSize(cb: SizeReadyCallback) {
                    }

                    override fun removeCallback(cb: SizeReadyCallback) {
                    }

                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        resource.saveGalleryAsVideo(view.context, true)
                        view.context.showToast("保存成功")
                    }
                })
    }

    override fun onBind(position: Int, data: PostVM) {
        super.onBind(position, data)
        if (data.userPostInfo.isVideo()) {
            viewModel = data
            vedioView.scaleType = ScaleType.FIT_XY

            vedioView.setOnClickListener {
                onClick()
            }
            reset()

            Glide.with(itemView.context)
                    .asFile()
                    .load(data.userPostInfo.post_video_url)
                    .into(loadTarget)
        }

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
            viewModel?.isMute = true
        } else {
            videoView.unMute()
            viewModel?.isMute = false
        }
    }

    private fun reset() {
        videoState = STATE_IDLE
        videoView.stop()
        videoLocalPath = null
        videoStopped()
    }


    override fun getVideoView(): TextureVideoView = vedioView

    override fun videoBeginning() {
        videoView.alpha = 1f
        cancelAlphaAnimate(coverView)
        startAlphaAnimate(coverView)
    }

    override fun videoStopped() {
        cancelAlphaAnimate(coverView)
        videoView.alpha = 0f
        coverView.alpha = 1f
        coverView.visibility = View.VISIBLE
    }

    override fun videoPrepared(player: MediaPlayer?) {
        muteVideo(PostVM.GloalbalMute)
    }

    private fun cancelAlphaAnimate(v: View) {
        ViewCompat.animate(v).cancel()
    }

    private fun startAlphaAnimate(v: View) {
        ViewCompat.animate(v).setListener(this).alpha(0f)
    }

    override fun videoResourceReady(videoPath: String?) {
        videoLocalPath = videoPath
        if (videoLocalPath != null) {
            videoView.setVideoPath(videoPath)
            if (videoState == STATE_ACTIVED) {
                videoView.start()
                muteVideo(PostVM.GloalbalMute)
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
            muteVideo(PostVM.GloalbalMute)
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

    override fun onAnimationEnd(view: View?) {
        view?.visibility = View.INVISIBLE
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun onAnimationStart(view: View?) {
    }


}