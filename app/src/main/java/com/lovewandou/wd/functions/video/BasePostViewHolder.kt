package com.lovewandou.wd.functions.video

import android.Manifest
import android.arch.lifecycle.LifecycleOwner
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.PopupMenu
import com.alien.newsdk.base.BaseDataBindingViewHolder
import com.alien.newsdk.extensions.autoSubscribeBy
import com.alien.newsdk.extensions.saveGalleryAsImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.lovewandou.wd.R
import com.lovewandou.wd.extension.rxrequestPermission
import com.lovewandou.wd.extension.showToast
import com.lovewandou.wd.functions.post.PostVM
import com.lovewandou.wd.functions.profile.ProfileVM
import com.lovewandou.wd.functions.share.ShareBottomDialog
import com.tencent.stat.StatService
import java.io.File
import java.util.*

/**
 * 描述:
 *
 * Created by and on 2018/11/7.
 */
open class BasePostViewHolder(val view: View,open val adapter:HomeMultiAdapter?=null) : BaseDataBindingViewHolder<PostVM>(view) {

    private val downloadBtn: View = view.findViewById(R.id.download_btn)
    private val shareBtn: View = view.findViewById(R.id.share_btn)
    private val moreBtn: View = view.findViewById(R.id.more_btn)
    private val mVideoCover:View = view.findViewById(R.id.video_cover)


    override fun onBind(position: Int, data: PostVM) {
        super.onBind(position, data)

        mVideoCover.setOnClickListener {

        }

        downloadBtn.setOnClickListener {
            (view.context as? FragmentActivity)?.rxrequestPermission("需要文件存储权限", "", Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (data.userPostInfo.isVideo()) {
                    onVideoDownload(view, data.userPostInfo.post_video_url)
                } else {
                    onImageDownload(view,data.userPostInfo.post_display_image)
                }
                val p = Properties()
                p.setProperty("name","download")
                StatService.trackCustomKVEvent(itemView.context,"buttonclick", p)
            }
        }

        shareBtn.setOnClickListener { it ->
            (it.context as? AppCompatActivity)?.let {
                ShareBottomDialog(it,data).show()
            }

        }

        moreBtn.setOnClickListener {
            val menu = PopupMenu(itemView.context,moreBtn)
            menu.menuInflater.inflate(R.menu.post_more_menu,menu.menu)
            menu.setOnMenuItemClickListener {
                if (it.itemId==R.id.report){
                    itemView.context.showToast("举报成功")
                }else{
                    ProfileVM(data.userPostInfo.toUserInfo()).cancelAttendUser(refreshFeed = adapter==null)
                            .autoSubscribeBy(itemView.context as? LifecycleOwner,onError = {
                                itemView.context.showToast("请重试")
                            }){
                                adapter?.remove(position)
                                adapter?.removeRepet(data.userPostInfo.user_id)
                                itemView.context.showToast("已拉黑")
                            }
                }

                return@setOnMenuItemClickListener  true
            }

            menu.show()
        }

    }

    fun onImageDownload(view: View, url: String) {
        Glide.with(view)
                .asFile()
                .load(url)
                .into(object : SimpleTarget<File>() {
                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        resource.saveGalleryAsImage(view.context)
                        view.context.showToast("保存成功")
                    }

                })
    }

    open fun onVideoDownload(view: View, url: String) {

    }
}