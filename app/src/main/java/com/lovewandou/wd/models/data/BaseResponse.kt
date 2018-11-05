package com.lovewandou.wd.models.data

import com.alien.newsdk.network.BaseHttpRespone

/**
 * 描述:
 *
 * Created by and on 2018/10/30.
 */
open class BaseResponse(val code:Int=0,val message:String="") : BaseHttpRespone{

    override fun isError(): Boolean = code == 40

    override fun getErroCode(): Int? = code

    override fun getErrorMsg(): String? = message
}