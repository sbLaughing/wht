package com.inwandou.wandou.models.data

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
data class LoginResp(
        val token:String="",
        val random:String="",
        val user:UserInfo = UserInfo()
):BaseResponse()


data class UserInfo(
        val mobile:String="",
        val nickName:String="",
        val avatar:String="",
        val type:Int=1,
        val id:String=""
){

    fun getLoginShowString(): String {
        return if (isValid()) mobile else "登录"
    }

    fun isValid(): Boolean {
        return !mobile.isEmpty() && !nickName.isEmpty()
    }
}