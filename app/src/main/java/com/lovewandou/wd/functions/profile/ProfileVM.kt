package com.lovewandou.wd.functions.profile

import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.alien.newsdk.network.async
import com.alien.newsdk.network.transformData
import com.lovewandou.wd.base.BaseSkipVM
import com.lovewandou.wd.models.AppData
import com.lovewandou.wd.models.data.BaseResponse
import com.lovewandou.wd.models.data.UserIdReq
import com.lovewandou.wd.models.data.UserInfo
import com.lovewandou.wd.models.data.UserPostInfo
import com.lovewandou.wd.network.RequestProvider
import io.reactivex.Maybe

/**
 * 描述:
 *
 * Created by and on 2018/11/4.
 */
class ProfileVM(var userInfo: UserInfo) : BaseSkipVM(), Parcelable {
    @Bindable
    fun isAttend(): Boolean {
        return userInfo.isAttend()
    }

    @Bindable
    fun getCurAttendString(): String {
        if (isAttend()) return "已关注"
        else return "关注"
    }

    fun fetchUserInfo(): Maybe<UserInfo> {
        val req = UserIdReq(user_id = userInfo.user_id)

        return RequestProvider.userRequest.getUserInfo(
                AppData.mGson.toJson(req))
                .async()
                .doOnSuccess {
                    userInfo = it
                    notifyChange()
                }
    }

    fun getUserPosts(userid: String): Maybe<List<UserPostInfo>> {
        val req = UserIdReq(userid)
        req.skip = skip
        req.limit = limit

        Log.e("ProfileVM","skip:$skip")
        return RequestProvider.userRequest
                .getPostsFromUser(AppData.mGson.toJson(req))
                .async()
    }

    fun toggleAttend(): Maybe<BaseResponse> {
        return if (isAttend()) {
            cancelAttendUser()
                    .doOnSuccess {
                        userInfo.cancelAttend()
                    }
        } else {
            attendUser()
                    .doOnSuccess {
                        userInfo.addAttend()
                    }
        }.doOnSuccess {
            notifyChange()
        }
    }

    fun attendUser(userid: String = userInfo.user_id): Maybe<BaseResponse> {
        return RequestProvider.userRequest.attendUser(AppData.mGson.toJson(UserIdReq(userid)))
                .async()
                .transformData()
    }

    fun cancelAttendUser(userid: String = userInfo.user_id): Maybe<BaseResponse> {
        return RequestProvider.userRequest.cancelAttendUser(AppData.mGson.toJson(UserIdReq(userid)))
                .async()
                .transformData()
    }

    constructor(source: Parcel) : this(
            source.readParcelable<UserInfo>(UserInfo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(userInfo, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProfileVM> = object : Parcelable.Creator<ProfileVM> {
            override fun createFromParcel(source: Parcel): ProfileVM = ProfileVM(source)
            override fun newArray(size: Int): Array<ProfileVM?> = arrayOfNulls(size)
        }
    }
}