package com.lovewandou.wd.functions.video

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean

/**
 * 描述:
 *
 * Created by and on 2018/10/25.
 */
class VideoViewModel (val remotePath:String): BaseObservable() {

    val isMute = ObservableBoolean(true)


}