package com.lovewandou.wd.functions.share

import android.app.Activity
import android.support.design.widget.BottomSheetDialog
import android.view.LayoutInflater
import android.view.View
import com.lovewandou.wd.R

/**
 * 描述:
 *
 * Created by and on 2018/10/18.
 */
class ShareBottomDialog (activity: Activity): BottomSheetDialog(activity,R.style.TransparentDialogStyle) {

    var rootview:View;
    init {

        rootview = LayoutInflater.from(activity).inflate(R.layout.dialog_share,null)
        setContentView(rootview)


    }

}