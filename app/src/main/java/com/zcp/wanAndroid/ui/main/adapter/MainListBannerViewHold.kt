package com.zcp.wanAndroid.ui.main.adapter

import android.view.View
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.zcp.wanAndroid.module.Banner
import com.zcp.wanAndroid.module.BannerData

class MainListBannerViewHold(
    itemView: View,
): RecyclerView.ViewHolder(itemView) {

    var bannerDates  = ObservableField<List<BannerData>>(listOf<BannerData>())
    var bannerInfoInit  = ObservableField<String>("")

    fun setData(banner: Banner?) {
        banner?.run {
            bannerDates.set(banner.data)
            bannerInfoInit.set(banner.data?.get(0)?.title)
        }
    }
}
