package com.zcp.wanAndroid.ui.main.adapter

import android.view.View
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.module.BannerData

class BannerViewHolder(
    private val itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    var bannerPlaceholder = ObservableField<Int>(R.drawable.placeholder)
    var bannerPhoto = ObservableField<String>("")

    fun setData(banner: BannerData?) {
        banner?.run {
            bannerPhoto.set(banner.imagePath)
        }
    }
}