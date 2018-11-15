package com.lovewandou.wd.base

import com.alien.newsdk.base.BaseVM

/**
 * 描述:
 *
 * Created by and on 2018/11/7.
 */
open class BaseSkipVM : BaseVM() {

    var skip:Int = 0
    var limit = 10


    fun resetPaging(){
        skip = 0;
    }

    fun firstPage():Boolean{
        return skip == 0
    }


}