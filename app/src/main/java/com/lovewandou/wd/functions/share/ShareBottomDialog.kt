package com.lovewandou.wd.functions.share

import android.content.Intent
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.alien.newsdk.extensions.autoSubscribeBy
import com.alien.newsdk.extensions.toByteArrayForWxShare
import com.alien.newsdk.network.async
import com.alien.newsdk.network.safeSubscribeBy
import com.bumptech.glide.Glide
import com.lovewandou.wd.R
import com.lovewandou.wd.functions.main.MainActivity
import com.lovewandou.wd.functions.post.PostVM
import com.sina.weibo.sdk.api.ImageObject
import com.sina.weibo.sdk.api.TextObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXImageObject
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import io.reactivex.Maybe
import kotlinx.android.synthetic.main.dialog_share.*


/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class ShareBottomDialog(val activity: AppCompatActivity, val vm: PostVM) : BottomSheetDialog(activity, R.style.TransparentDialogStyle) {

    var rootview: View;

    val shareUrl = "https://api.lovewht.com/share.html?post_id=${vm.userPostInfo.post_id}"


    init {

        rootview = LayoutInflater.from(activity).inflate(R.layout.dialog_share, null)
        setContentView(rootview)

        weibo_share_tv.setOnClickListener {
            Maybe.create<WeiboMultiMessage> {
                val weiboMessage = WeiboMultiMessage()
                val textObject = TextObject()
                textObject.text = vm.userPostInfo.getWeiboShareIntroName() + "的摄影作品" + shareUrl
                weiboMessage.textObject = textObject

                val imageObject = ImageObject()
                imageObject.setImageObject(getShareThum())
                weiboMessage.imageObject = imageObject
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

        listOf(wechat_circle_share_tv, wechat_share_tv)
                .forEach { view ->
                    view.setOnClickListener {
                        Maybe.create<SendMessageToWX.Req> {
                            val req = SendMessageToWX.Req()
                            if (vm.userPostInfo.isVideo()) {
                                val videoObj = WXWebpageObject()
                                videoObj.webpageUrl = vm.userPostInfo.post_video_url
                                val msg = WXMediaMessage(videoObj)
                                msg.thumbData = getShareThum().toByteArrayForWxShare()
                                msg.title = vm.userPostInfo.getIntroName() + "的摄影作品"
//                                msg.description = vm.userPostInfo.post_caption
                                req.transaction = "video"
                                req.message = msg
                            } else {
                                if (view.id == R.id.wechat_share_tv) {
                                    val imageObj = WXImageObject(getShareImage())
                                    val msg = WXMediaMessage()
                                    msg.mediaObject = imageObj
                                    req.transaction = "img"
                                    req.message = msg
                                } else {
                                    val webObj = WXWebpageObject()
                                    webObj.webpageUrl = shareUrl
                                    val msg = WXMediaMessage(webObj)
                                    msg.thumbData = getShareThum().toByteArrayForWxShare()
                                    msg.title = vm.userPostInfo.getIntroName() + "的摄影作品"
//                                msg.description = vm.userPostInfo.post_caption
                                    req.transaction = "web"
                                    req.message = msg
                                }

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

        more_share_tv.setOnClickListener {
            Maybe.create<Intent> {
                val intent = Intent(Intent.ACTION_SEND)
                if (vm.userPostInfo.isVideo()) {
                    intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(activity, activity.packageName + ".provider", getShareVideo()))
                    intent.setType("video/*")
                } else {
                    val file = getShareImageFile()
                    intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(activity, activity.packageName + ".provider", file))
                    intent.setType("image/*")
                }
                it.onSuccess(intent)
            }.async()
                    .doAfterTerminate {
                        dismiss()
                    }
                    .safeSubscribeBy {
                        activity.startActivity(Intent.createChooser(it, "分享到"))
                    }

        }
    }

    fun getShareVideo() = Glide.with(activity.applicationContext)
            .asFile()
            .load(vm.userPostInfo.post_video_url)
            .submit()
            .get()

    fun getShareImage() = Glide.with(activity.applicationContext)
            .asBitmap()
            .load(vm.userPostInfo.post_display_image)
            .submit()
            .get()


    fun getShareThum() = Glide.with(activity.applicationContext)
            .asBitmap()
            .load(vm.userPostInfo.post_thumbnail)
            .submit()
            .get()

    fun getShareImageFile() = Glide.with(activity.applicationContext)
            .asFile()
            .load(vm.userPostInfo.post_display_image)
            .submit()
            .get()

}