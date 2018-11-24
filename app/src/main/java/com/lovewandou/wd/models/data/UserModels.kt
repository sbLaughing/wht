package com.lovewandou.wd.models.data

import android.os.Parcel
import android.os.Parcelable
import com.lovewandou.wd.functions.video.IMultiItem
import org.joda.time.*

/**
 * 描述:
 *
 * Created by and on 2018/11/4.
 */
class UserIdReq(
        val user_id:String
):BaseRequest()

data class UserPostInfo(
        val user_id: String = "",
        val thumbnail: String = "",
        val user_name: String = "",
        val user_name_zh: String = "",
        val full_name: String = "",
        val post_display_image: String = "",
        val post_link: String = "",
        val post_thumbnail: String = "",
        val post_video_url: String = "",
        val post_date: String = "",
        val post_id: String = "",
        val post_caption: String = "",
        val post_is_video: Boolean = false,
//       val _id:String=",
        val post_caption_zh: String = ""
) : Parcelable, IMultiItem {
    fun isVideo(): Boolean {
        return post_is_video
    }

    var isExpand = false

    override val itemType: Int
        get() = if (post_is_video) 1 else 0

    fun toUserInfo(): UserInfo {
        val ret = UserInfo(thumbnail = thumbnail, user_name = user_name, user_id = user_id, is_attend = null)
        return ret
    }

    fun getTimeString(): String {
        val current = DateTime.now()
        val target = DateTime((post_date.toLongOrNull() ?: 0) * 1000)
        val diffDay = Days.daysBetween(target, current).days
        val diffSecond = Seconds.secondsBetween(target, current).seconds
        val diffMinute = Minutes.minutesBetween(target, current).minutes
        val diffHour = Hours.hoursBetween(target, current).hours
        return if (diffSecond < 60) {
            "$diffSecond 秒前"
        } else if (diffMinute < 60) {
            "$diffMinute 分钟前"
        } else if (diffHour < 24) {
            "$diffHour 小时前"
        } else {
            "$diffDay 天前"
//        }else if (Math.abs(diffDay)<5){
//            "$diffDay 天前"
//        }else {
//            if (current.year == target.year) target.toString("MM-dd HH:mm")
//            else target.toString("yyyy-MM-dd HH:mm")
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(user_id)
        writeString(thumbnail)
        writeString(user_name)
        writeString(user_name_zh)
        writeString(full_name)
        writeString(post_display_image)
        writeString(post_link)
        writeString(post_thumbnail)
        writeString(post_video_url)
        writeString(post_date)
        writeString(post_id)
        writeString(post_caption)
        writeInt((if (post_is_video) 1 else 0))
        writeString(post_caption_zh)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserPostInfo> = object : Parcelable.Creator<UserPostInfo> {
            override fun createFromParcel(source: Parcel): UserPostInfo = UserPostInfo(source)
            override fun newArray(size: Int): Array<UserPostInfo?> = arrayOfNulls(size)
        }
    }
}