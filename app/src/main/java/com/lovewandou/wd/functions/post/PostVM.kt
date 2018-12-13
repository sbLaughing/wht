package com.lovewandou.wd.functions.post

import android.databinding.Bindable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import com.alien.newsdk.base.BaseVM
import com.lovewandou.wd.BR
import com.lovewandou.wd.R
import com.lovewandou.wd.WDApp
import com.lovewandou.wd.functions.video.IMultiItem
import com.lovewandou.wd.models.data.UserPostInfo

/**
 * 描述:
 *
 * Created by and on 2018/11/5.
 */
class PostVM (val userPostInfo: UserPostInfo): BaseVM() ,IMultiItem{

    companion object {
        var GloalbalMute = true
    }


    var isExpanded = false

    override val itemType: Int
        get() = userPostInfo.itemType


    var isMute = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.muteIcon)
            GloalbalMute = field
        }

    fun getPostContentStr(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        if (userPostInfo.post_caption.isNullOrEmpty()) return sb
        sb.append(SpannableString(userPostInfo.full_name).apply {
            setSpan(TextAppearanceSpan(WDApp.context,R.style.PostBoldTextAppearance),0,this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        })
        sb.append("  ")
        sb.append(userPostInfo.post_caption?:"")
        return sb
    }

    @Bindable
    fun getMuteIcon():Int?{
        return if (userPostInfo.post_is_video){
            if (isMute) R.drawable.silent else R.drawable.sound
        }
        else null
    }

}
