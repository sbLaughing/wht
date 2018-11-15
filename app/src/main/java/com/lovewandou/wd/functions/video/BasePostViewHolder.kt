package com.lovewandou.wd.functions.video

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.provider.MediaStore
import android.support.v4.app.FragmentActivity
import android.view.View
import com.alien.newsdk.base.BaseDataBindingViewHolder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.lovewandou.wd.R
import com.lovewandou.wd.extension.rxrequestPermission
import com.lovewandou.wd.extension.showToast
import com.lovewandou.wd.functions.post.PostVM
import com.lovewandou.wd.functions.share.ShareBottomDialog
import com.lovewandou.wd.functions.video.glide.GlideApp

/**
 * 描述:
 *
 * Created by and on 2018/11/7.
 */
open class BasePostViewHolder(val view: View) : BaseDataBindingViewHolder<PostVM>(view) {

    private val downloadBtn: View = view.findViewById(R.id.download_btn)
    private val shareBtn: View = view.findViewById(R.id.share_btn)
    private val moreBtn: View = view.findViewById(R.id.more_btn)


    override fun onBind(position: Int, data: PostVM) {
        super.onBind(position, data)

        downloadBtn.setOnClickListener {
            (view.context as? FragmentActivity)?.rxrequestPermission("需要文件存储权限", "", Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (data.userPostInfo.isVideo()) {
                    onVideoDownload(view, data.userPostInfo.post_video_url)
                } else {
                    onImageDownload(view,data.userPostInfo.post_display_image)
                }
            }
        }

        shareBtn.setOnClickListener { it ->
            (it.context as? Activity)?.let {
                ShareBottomDialog(it).show()
            }

        }

    }

    fun onImageDownload(view: View, url: String) {
        GlideApp.with(view)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        MediaStore.Images.Media.insertImage(view.context.contentResolver, resource, "", "")
                        view.context.showToast("保存成功")
                    }

                })
    }

    open fun onVideoDownload(view: View, url: String) {

    }
}