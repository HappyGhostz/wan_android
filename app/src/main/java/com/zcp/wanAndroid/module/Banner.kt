package com.zcp.wanAndroid.module

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
    val data: List<BannerData>? = null,
    val errorCode: Int = 0,
    val errorMsg: String? = null
) : Parcelable {
}


@Parcelize
data class BannerData(
    var desc: String? = null,
    var id: Int = 0,
    var imagePath: String? = null,
    var isVisible: Int = 0,
    var order: Int = 0,
    var title: String? = null,
    var type: Int = 0,
    var url: String? = null
) : Parcelable {
}
