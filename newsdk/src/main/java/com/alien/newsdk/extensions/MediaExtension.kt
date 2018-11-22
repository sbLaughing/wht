package com.alien.newsdk.extensions

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File



/**
 * 描述:
 *
 * Created by and on 2018/11/21.
 */
val galleryPath = (Environment.getExternalStorageDirectory().absolutePath + File.separator
        + File.separator + "Camera" + File.separator)

fun File.saveVideo2Gallery(context: Context){
//    if (!this.exists()) return
//    val targetPath = galleryPath+this.name
//    val targetFile = File(targetPath)
//    this.copyTo(File(targetPath),true)
//    MediaStore.Video.Media.insertImage(context.contentResolver,
//            targetFile.absolutePath, targetFile.name, null)
//    scanMedia(context,targetFile)

}

//针对非系统影音资源文件夹
fun insertIntoMediaStore(context: Context, isVideo: Boolean, saveFile: File, createTime: Long) {
    var createTime = createTime
    val mContentResolver = context.contentResolver
    if (createTime == 0L)
        createTime = System.currentTimeMillis()
    val values = ContentValues()
    values.put(MediaStore.MediaColumns.TITLE, saveFile.name)
    values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.name)
    //值一样，但是还是用常量区分对待
    values.put(if (isVideo)
        MediaStore.Video.VideoColumns.DATE_TAKEN
    else
        MediaStore.Images.ImageColumns.DATE_TAKEN, createTime)
    values.put(MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis())
    values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis())
    if (!isVideo)
        values.put(MediaStore.Images.ImageColumns.ORIENTATION, 0)
    values.put(MediaStore.MediaColumns.DATA, saveFile.absolutePath)
    values.put(MediaStore.MediaColumns.SIZE, saveFile.length())
    values.put(MediaStore.MediaColumns.MIME_TYPE, if (isVideo) "video/mp4" else "image/jpeg")
    //插入
    mContentResolver.insert(if (isVideo)
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    else
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
}

fun File.saveImage2Gallery(context: Context){
    if (!this.exists()) return
    val targetPath = galleryPath +this.name
    val targetFile = File(targetPath)
    this.copyTo(targetFile,true)
    MediaStore.Images.Media.insertImage(context.contentResolver,
            targetFile.absolutePath, targetFile.name, null)
    scanMedia(context, targetFile)
}

private fun scanMedia(context : Context,path:File){
    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    val uri = Uri.fromFile(path)
    intent.data = uri
    context.sendBroadcast(intent)
}