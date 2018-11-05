package com.lovewandou.wd.extension

import android.content.res.Resources
import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.lovewandou.wd.functions.video.glide.GlideApp

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

@BindingAdapter(value = ["app:imageUrl"])
fun bindImageUrl(v: ImageView, url:String?=null){
    GlideApp.with(v).load(url).into(v)
}

@BindingAdapter(value = ["app:circleAvatar"])
fun bindCircleAvatar(v: ImageView, url:String?=null){
    url?.let {
        GlideApp.with(v).load(url).apply(RequestOptions.circleCropTransform()).into(v)
    }
}

