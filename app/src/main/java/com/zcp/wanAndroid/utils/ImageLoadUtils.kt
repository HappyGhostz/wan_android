package com.zcp.wanAndroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class ImageLoadUtils {
    fun loadImageViewWithUrl(context: Context, url: String?, imageView: ImageView) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context).load(url).into(imageView)
        }
    }

    fun loadImageViewWithPlaceholderId(
        context: Context,
        url: String?,
        imageView: ImageView,
        placeholderId: Int
    ) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context).load(url).placeholder(placeholderId).into(imageView)
        }
    }

    fun loadImage(
        src: String?,
        placeholder: Int?,
        error: Int?,
        roundedCorner: Int?,
        fitCenter: Boolean,
        imageView: ImageView
    ) {
        val manager = Glide.with(imageView.context)

        var requestBuilder = src.let {
            manager.load(src)
        }

        if (placeholder == null) {
            requestBuilder = requestBuilder.error(error)
        } else {
            requestBuilder.error(error).placeholder(placeholder)
        }

        if (fitCenter) {
            requestBuilder.fitCenter()
        } else {
            requestBuilder.centerCrop()
        }

        if (roundedCorner != null) {
            //设置图片圆角角度
            //设置图片圆角角度
            val roundedCorners = RoundedCorners(roundedCorner) //px

            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            val options = RequestOptions.bitmapTransform(roundedCorners)
            requestBuilder = requestBuilder.apply(options)
        }

        requestBuilder.into(imageView)
    }
}