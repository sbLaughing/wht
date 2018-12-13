package com.lovewandou.wd.models.data

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.lovewandou.wd.R

/**
 * 描述:
 *
 * Created by and on 2018/11/3.
 */
data class SearchReq(
        val keyword:String?=null,
        val tag:String?=null
):BaseRequest()



data class TagsInfo(
        val name:String
)


data class SearchWrapper(val key:String,val result:List<UserInfo>)

data class UserInfo(
        val products: List<UserPostInfo> = listOf(),
        val user_name: String = "",
        val user_name_zh: String = "",
        val full_name: String = "",
        private var is_attend: Int? = 0,
        val user_id: String = "",
        val thumbnail: String = ""
) : Parcelable {
    fun getNameFromSubFind(): String {
        if (user_name_zh.isNullOrEmpty()) return full_name
        else return full_name + "（${user_name_zh}）"
    }

    fun isAttend(): Boolean {
        return (is_attend == 1)
    }

    fun getIsAttendString(): String {
        return if (is_attend == 1) {
            "已关注"
        } else {
            "关注"
        }
    }

    fun isAttendValid(): Boolean {
        return is_attend != null
    }

    fun cancelAttend() {
        is_attend = 0
    }

    fun addAttend() {
        is_attend = 1
    }

    fun getAttendStringColor(): Int? {
        return if (is_attend == 1) {
            R.color.comGreyTextColor
        } else {
            R.color.colorAccent
        }
    }

    fun getPreviewImage0(): UserPostInfo? {
        return if (products.isEmpty()) null
        else products[0]
    }

    fun getPreviewImage1(): UserPostInfo? {
        return if (products.size < 2) null
        else products[1]
    }

    fun getPreviewImage2(): UserPostInfo? {
        return if (products.size < 3) null
        else products[2]
    }

    fun isPreviewVideo0(): Int {
        return if (products.isEmpty() || !products[0].isVideo()) View.GONE
        else View.VISIBLE
    }

    fun isPreviewVideo1(): Int {
        return if (products.size < 2 || !products[1].isVideo()) View.GONE
        else View.VISIBLE
    }

    fun isPreviewVideo2(): Int {
        return if (products.size < 3 || !products[2].isVideo()) View.GONE
        else View.VISIBLE
    }

    constructor(source: Parcel) : this(
            source.createTypedArrayList(UserPostInfo.CREATOR),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(products)
        writeString(user_name)
        writeString(user_name_zh)
        writeString(full_name)
        writeValue(is_attend)
        writeString(user_id)
        writeString(thumbnail)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserInfo> = object : Parcelable.Creator<UserInfo> {
            override fun createFromParcel(source: Parcel): UserInfo = UserInfo(source)
            override fun newArray(size: Int): Array<UserInfo?> = arrayOfNulls(size)
        }
    }
}

data class UserProductsPreview(
        val post_thumbnail: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(post_thumbnail)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserProductsPreview> = object : Parcelable.Creator<UserProductsPreview> {
            override fun createFromParcel(source: Parcel): UserProductsPreview = UserProductsPreview(source)
            override fun newArray(size: Int): Array<UserProductsPreview?> = arrayOfNulls(size)
        }
    }
}