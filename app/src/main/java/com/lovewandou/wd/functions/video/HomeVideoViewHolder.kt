package com.lovewandou.wd.functions.video

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.lovewandou.wd.BuildConfig
import com.lovewandou.wd.R
import com.lovewandou.wd.extension.showToast
import com.lovewandou.wd.functions.post.PostVM
import com.lovewandou.wd.functions.video.glide.GlideApp
import com.lovewandou.wd.util.FileUtils
import com.waynell.videolist.visibility.items.ListItem
import com.waynell.videolist.widget.ScaleType
import com.waynell.videolist.widget.TextureVideoView
import java.io.File


/**
 * 描述:
 *
 * Created by and on 2018/10/24.
 */
class HomeVideoViewHolder(view: View) : BasePostViewHolder(view), VideoLoadMvpView, ListItem, ViewPropertyAnimatorListener {



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
        GlideApp.with(view.context)
                .asFile()
                .load(url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .downloadOnly(object : SimpleTarget<File>(){
                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        val trailStr = url.split("/").last()
                        val newFile = File(view.context.getExternalFilesDir(Environment.DIRECTORY_MOVIES),trailStr)
                        resource.copyTo(newFile,overwrite = true)
                        val cr = view.context.contentResolver
                        val cv = FileUtils.getVideoContentValues(view.context,newFile,System.currentTimeMillis())
                        cr.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,cv)

//                        view.context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                                Uri.fromFile(newFile)))
                        val localIntent = Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE")
                        val localUri = Uri.fromFile(newFile)
                        localIntent.data = localUri
                        view.context.sendBroadcast(localIntent)
                        view.context.showToast("保存成功")
                    }
                })
    }
    override fun onBind(position: Int, data: PostVM) {
        super.onBind(position, data)
        viewModel = data
        vedioView.scaleType = ScaleType.FIT_XY

        vedioView.setOnClickListener {
            onClick()
        }
        reset()

        GlideApp.with(itemView.context)
                .asFile()
                .load(data.userPostInfo.post_video_url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .downloadOnly(loadTarget)

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
        muteVideo(true)
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

    override fun onAnimationEnd(view: View?) {
        view?.visibility = View.INVISIBLE
    }

    override fun onAnimationCancel(view: View?) {//To change body of created functions use File | Settings | File Templates.
    }

    override fun onAnimationStart(view: View?) {//To change body of created functions use File | Settings | File Templates.
    }


}