package com.lovewandou.wd.extension

import android.content.res.Resources
import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lovewandou.wd.R
import com.lovewandou.wd.WDApp
import com.lovewandou.wd.functions.post.PostVM

/**
 * 描述:
 *
 * Created by and on 2018/11/3.
 */
@BindingAdapter(value = ["android:textColor"])
fun bindTextColor(v: TextView, resource: Int? = null) {
    resource?.let {
        try {
            v.setTextColor(ContextCompat.getColor(v.context, it))
        } catch (e: Resources.NotFoundException) {
            v.setTextColor(it)
        }

    }
}

private val appendText:String  = " ...展开"

@BindingAdapter(value = ["app:postContent"])
fun bindPostContent(v:TextView,vm:PostVM){
    v.post {
        if (v==null) return@post
        v.setOnClickListener {
            if (vm.isExpanded) return@setOnClickListener
            vm.isExpanded = true
            v.text = vm.getPostContentStr()
            return@setOnClickListener
        }
        var text = vm.getPostContentStr()
        v.text = text
        val layout = v.layout
        if (layout?.lineCount?:0<=2 || vm.isExpanded){
            return@post
        }
        val lineEnd0 = layout.getLineEnd(0)
        val lineEnd1 = layout.getLineEnd(1)
        val text0 = text.subSequence(0,lineEnd0)
        val text1 = text.substring(lineEnd0,lineEnd1).replace("\n","")

        val standardLineWidth = layout.width
        val lineTextWidth1 = v.paint.measureText(text1)
        val appendTextWidth = v.paint.measureText(appendText)
        if (lineTextWidth1+appendTextWidth<=standardLineWidth){
            v.text = SpannableStringBuilder().apply {
                append(text0)
                append(text1)
                append(SpannableString(appendText).apply {
                    setSpan(TextAppearanceSpan(WDApp.context, R.style.PostBoldTextAppearance), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                })
            }
        }else {
            for (i in lineEnd1 - lineEnd0 downTo 0) {
                if (i>text1.length) continue
                if (v.paint.measureText(text1.substring(0, i) + appendText)+12 < standardLineWidth) {
                    v.text = SpannableStringBuilder().apply {
                        append(text0)
                        append(text1.substring(0, i))
                        append(SpannableString(appendText).apply {
                            setSpan(TextAppearanceSpan(WDApp.context, R.style.PostBoldTextAppearance), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        })

                    }
                    return@post
                }
            }
        }
    }
}

@BindingAdapter(value = ["app:imageUrl","app:thumbUrl"],requireAll = false)
fun bindImageUrl(v: ImageView, url: String? = null,thumb:String?=null) {
    var req = Glide.with(v).load(url)
            if (!thumb.isNullOrEmpty()){
                req = req.thumbnail(Glide.with(v).load(thumb))
            }else{
//                req = req.thumbnail(0.3f)
            }
            req.into(v)
}

@BindingAdapter(value = ["app:circleAvatar"])
fun bindCircleAvatar(v: ImageView, url: String? = null) {
    url?.let {
        Glide.with(v.context)
                .load(url)
                .apply(RequestOptions.placeholderOf(R.drawable.tourists_avatar))
                .apply(RequestOptions.circleCropTransform())
                .into(v)
    }?:v.setImageResource(R.drawable.tourists_avatar)

}

@BindingAdapter(value = ["android:src"])
fun bindImageRes(v: ImageView, resource: Int?) {
    resource?.let {
        v.setImageResource(it)
    }
}