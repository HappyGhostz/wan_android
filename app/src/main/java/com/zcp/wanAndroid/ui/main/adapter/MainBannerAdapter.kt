package com.zcp.wanAndroid.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.youth.banner.adapter.BannerAdapter
import com.zcp.wanAndroid.databinding.ItemBannerViewHolderBinding
import com.zcp.wanAndroid.module.BannerData
import com.zcp.wanAndroid.utils.ImageLoadUtils

class MainBannerAdapter(
    banners: List<BannerData>,
) :
    BannerAdapter<BannerData, BannerViewHolder>(banners) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val binding =
            ItemBannerViewHolderBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        val holder = BannerViewHolder(binding.root)
        binding.holder = holder
        return holder
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: BannerData?,
        position: Int,
        size: Int
    ) {
        holder?.setData(data)
    }
}