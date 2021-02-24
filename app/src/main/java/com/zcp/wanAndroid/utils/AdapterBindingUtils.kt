package com.zcp.wanAndroid.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.youth.banner.Banner
import com.zcp.wanAndroid.extension.loadImage
import com.zcp.wanAndroid.module.BannerData
import com.zcp.wanAndroid.ui.main.adapter.MainBannerAdapter

@BindingAdapter("banner:bannerAdapterBinding")
fun setBannerAdapterBinding(view: Banner<BannerData, MainBannerAdapter>, dataSet: List<BannerData>) {
    view.setDatas(dataSet)
}

@BindingAdapter(value = ["src", "placeholder", "error"], requireAll = false)
fun bindSrc(image: ImageView, src: String?, placeholder: Int?, error: Int?) {
    image.loadImage(src, placeholder, error, isFitCenter = true)
}