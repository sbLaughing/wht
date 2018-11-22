package com.lovewandou.wd.models.data

import java.io.Serializable

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
data class LoginResp(
        val token:String="",
        val random:String="",
        val user:AccountInfo = AccountInfo()
):BaseResponse()


data class AccountInfo(
        val mobile:String="",
        val user_name:String="",
        val thumbnail:String="",
        val type:Int=1,
        val user_id:String=""
):Serializable{

    fun getAvatar():String?{
        return if (thumbnail.isEmpty()) null else thumbnail

    }

    fun getLoginShowString(): String {
        return user_name
    }


    fun isValid():Boolean = user_id.isNotEmpty()
}