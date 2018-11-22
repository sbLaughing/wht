package com.lovewandou.wd.extension

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.alien.newsdk.network.safeSubscribeBy
import com.alien.newsdk.util.MPermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * 描述:
 *
 * Created by and on 2018/11/7.
 */
fun Context?.showToast(str:String){
    Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.rxrequestPermission(title: String, content: String, vararg array: String, onDenied: () -> Unit = {}, onGrant: () -> Unit = {}) {
    RxPermissions(this)
            .requestEachCombined(*array)
            .doOnError {
                onDenied()
            }
            .safeSubscribeBy {
                if (it.granted) {
                    onGrant()
                } else if (it.shouldShowRequestPermissionRationale) {
                    onDenied()
                    if (title.isNotEmpty() || content.isNotEmpty())
                        showPermissionDialog(this) {
                            this.title(title)
                                    .content(content)
                                    .onPositive { dialog, which ->
                                        rxrequestPermission(title, content, *array, onDenied = onDenied, onGrant = onGrant)
                                    }
                        }
                } else {
                    onDenied()
                    if (title.isNotEmpty() || content.isNotEmpty())
                        showPermissionDialog(this, showAsNeverAsk = true) {
                            this.title(title)
                                    .content(content)
                        }
                }
            }
}

fun showPermissionDialog(activity: Activity?, positiveText: String = "允许", showAsNeverAsk: Boolean = false, builderApply: MaterialDialog.Builder.() -> Unit = {}) {
    activity ?: return
    val builder = MaterialDialog.Builder(activity)
    builder.apply(builderApply)
    if (showAsNeverAsk)
        builder.onPositive { dialog, which ->
            MPermissionUtil.startAppSettings(activity)
        }
    builder.positiveText(if (showAsNeverAsk) "前往设置" else positiveText)
            .negativeText("取消")
            .show()
}

//fun HRSupportFragment<*>.rxrequestPermission(title: String, content: String, vararg array: String, onDenied: () -> Unit = {}, onGrant: () -> Unit = {}) {
//    this._mActivity.rxrequestPermission(title, content, *array, onDenied = onDenied, onGrant = onGrant)
//}