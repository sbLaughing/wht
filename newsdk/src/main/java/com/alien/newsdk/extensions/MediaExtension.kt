package com.alien.newsdk.extensions

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer


/**
 * 描述:
 *
 * Created by and on 2018/11/21.
 */
val galleryPath = (Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_DCIM
        + File.separator + "Camera" + File.separator)

fun File.saveGalleryAsVideo(context: Context,appendTrailing :Boolean = true){
    if (!this.exists()) return
    val targetPath = if (appendTrailing){
        galleryPath +this.name + ".mp4"
    }else{
        galleryPath +this.name
    }
    val targetFile = File(targetPath)
    this.copyTo(targetFile,true)
//    MediaStore.Images.Media.insertImage(context.contentResolver,
//            targetFile.absolutePath, targetFile.name, null)
    scanMedia(context, targetFile)

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

fun File.saveGalleryAsImage(context: Context, appendTrailing :Boolean = true){
    if (!this.exists()) return
    val targetPath = if (appendTrailing){
        galleryPath +this.name + ".jpg"
    }else{
        galleryPath +this.name
    }
    val targetFile = File(targetPath)
    this.copyTo(targetFile,true)
//    MediaStore.Images.Media.insertImage(context.contentResolver,
//            targetFile.absolutePath, targetFile.name, null)
    scanMedia(context, targetFile)
}

private fun scanMedia(context : Context,path:File){
    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    val uri = Uri.fromFile(path)
    intent.data = uri
    context.sendBroadcast(intent)
}

fun saveBitmap2File(bitmap: Bitmap, path: File): Boolean {
    if (path.exists()) {
        path.delete()
    }else{

    }

    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(path)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        fos.flush()
        return true
    } catch (ignore: Exception) {
        ignore.printStackTrace()
        return false
    } finally {
        fos?.close()
    }
}

fun Bitmap.save2File(file: File):Boolean{
    return saveBitmap2File(this,file)
}

fun Bitmap.toByteArrayByBuffer(): ByteArray{
    val buffer = ByteBuffer.allocate(byteCount)
    this.copyPixelsToBuffer(buffer)
    return buffer.array()
}

fun Bitmap.toByteArrayForWxShare(): ByteArray{
    val byteArray = BitmapUtil.compressByQuality(this,30000,true)
    Log.e("MediaExtension","bitmap to bytearray size:${byteArray.size}")
    return byteArray
}