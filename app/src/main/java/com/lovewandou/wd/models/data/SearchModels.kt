package com.lovewandou.wd.models.data

import android.os.Parcel
import android.os.Parcelable
import com.lovewandou.wd.R

/**
 * 描述:
 *
 * Created by and on 2018/11/3.
 */
data class SearchReq(
        val keyword:String?=null,
        val tag:String?=null
)



data class TagsInfo(
        val name:String
)

data class UserInfo(
        val products: List<UserProductsPreview> = listOf(),
        val user_name: String = "",
        private var is_attend: Int = 0,
        val user_id: String = "",
        val thumbnail: String = ""
) : Parcelable {

    fun isAttend():Boolean{
        return (is_attend==1)
    }
    fun getIsAttendString(): String {
        return if (is_attend == 1) {
            "已关注"
        } else {
            "关注"
        }
    }

    fun cancelAttend(){
        is_attend = 0
    }

    fun addAttend(){
        is_attend = 1
    }

    fun getAttendStringColor(): Int? {
        return if (is_attend == 1) {
            R.color.comGreyTextColor
        } else {
            R.color.colorAccent
        }
    }

    fun getPreviewImage0(): String? {
        return if (products.isEmpty()) null
        else products[0].post_thumbnail
    }

    fun getPreviewImage1(): String? {
        return if (products.size < 2) null
        else products[1].post_thumbnail
    }

    fun getPreviewImage2(): String? {
        return if (products.size < 3) null
        else products[2].post_thumbnail
    }

    constructor(source: Parcel) : this(
            ArrayList<UserProductsPreview>().apply { source.readList(this, UserProductsPreview::class.java.classLoader) },
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeList(products)
        writeString(user_name)
        writeInt(is_attend)
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
        val post_thumbnail:String=""
)