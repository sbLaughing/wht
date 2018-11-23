package com.lovewandou.wd.functions.share

import android.net.Uri
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.alien.newsdk.extensions.autoSubscribeBy
import com.alien.newsdk.network.async
import com.lovewandou.wd.R
import com.lovewandou.wd.functions.main.MainActivity
import com.lovewandou.wd.functions.post.PostVM
import com.lovewandou.wd.functions.video.glide.GlideApp
import com.sina.weibo.sdk.api.ImageObject
import com.sina.weibo.sdk.api.TextObject
import com.sina.weibo.sdk.api.VideoSourceObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXImageObject
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXVideoObject
import io.reactivex.Maybe
import kotlinx.android.synthetic.main.dialog_share.*


/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class ShareBottomDialog(val activity: AppCompatActivity, val vm: PostVM) : BottomSheetDialog(activity, R.style.TransparentDialogStyle) {

    var rootview: View;



    init {

        rootview = LayoutInflater.from(activity).inflate(R.layout.dialog_share, null)
        setContentView(rootview)

        weibo_share_tv.setOnClickListener {
            Maybe.create<WeiboMultiMessage> {
                val weiboMessage = WeiboMultiMessage()
                val textObject = TextObject()
                textObject.text = vm.userPostInfo.post_caption
                weiboMessage.textObject = textObject

                if (vm.userPostInfo.isVideo()) {
                    val videoSourceObject = VideoSourceObject()
                    videoSourceObject.videoPath = Uri.fromFile(getShareVideo())
                    weiboMessage.videoSourceObject = videoSourceObject
                } else {
                    val imageObject = ImageObject()
                    imageObject.setImageObject(getShareImage())
                    weiboMessage.imageObject = imageObject
                }
                it.onSuccess(weiboMessage)
            }
                    .doAfterTerminate {
                        dismiss()
                    }
                    .async()
                    .autoSubscribeBy(activity) {
                        (activity as? MainActivity)?.shareHandler?.shareMessage(it, true)
                    }
        }

        listOf(wechat_circle_share_tv,wechat_share_tv)
                .forEach { view->
                    view.setOnClickListener {
                        Maybe.create<SendMessageToWX.Req> {
                            val req = SendMessageToWX.Req()
                            if (vm.userPostInfo.isVideo()){
                                val videoObj = WXVideoObject()
                                videoObj.videoUrl = vm.userPostInfo.post_video_url
                                val msg = WXMediaMessage(videoObj)
                                msg.title = vm.userPostInfo.full_name
                                msg.description = vm.userPostInfo.post_caption
                                req.transaction = "video"
                                req.message = msg
                            }else{
                                val imageObj = WXImageObject(getShareImage())
                                val msg = WXMediaMessage()
                                msg.mediaObject = imageObj
                                req.transaction = "img"
                                req.message = msg
                            }

                            if (view.id == R.id.wechat_share_tv)
                                req.scene = SendMessageToWX.Req.WXSceneSession
                            else {
                                req.scene = SendMessageToWX.Req.WXSceneTimeline
                            }

                            it.onSuccess(req)
                        }.async()
                                .doAfterTerminate {
                                    dismiss()
                                }
                                .autoSubscribeBy(activity) {
                                    (activity as? MainActivity)?.wxApi?.sendReq(it)
                                }



                    }
                }
    }

    fun getShareVideo() =GlideApp.with(activity.applicationContext)
            .asFile()
            .load(vm.userPostInfo.post_video_url)
            .submit()
            .get()

    fun getShareImage() = GlideApp.with(activity.applicationContext)
                .asBitmap()
                .load(vm.userPostInfo.post_display_image)
                .submit()
                .get()

}