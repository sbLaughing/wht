package com.alien.newsdk.network

/**
 * 描述:
 *
 * Created by Alien on 2017/12/8.
 */
open class ApiException(val code:Int?=0, override val message:String?=null) :Exception(message)
class ApiConnectExceptioin constructor(code: Int?,message: String?): ApiException(code,message)

class OtherException(val code:Int?=0, override val message:String?=null) :Exception(message)
class IgnoreException(val code:Int?=0,override val message:String?=null) :Exception(message)