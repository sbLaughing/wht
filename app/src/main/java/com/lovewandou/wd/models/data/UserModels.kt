package com.lovewandou.wd.models.data

/**
 * 描述:
 *
 * Created by and on 2018/11/4.
 */
data class UserIdReq(
        val user_id:String
)

data class UserPostInfo(
       val user_id:String,
       val post_display_image:String,
       val post_link:String,
       val post_thumbnail:String,
       val post_video_url:String,
       val post_date:String,
       val post_id:String,
       val post_caption:String,
       val post_is_video:Boolean=false,
//       val _id:String,
       val post_caption_zh:String
)