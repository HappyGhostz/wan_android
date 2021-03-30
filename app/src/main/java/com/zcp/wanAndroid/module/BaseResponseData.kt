package com.zcp.wanAndroid.module

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseResponseData(
    val errorCode: Int = 0,
    val errorMsg: String? = null
) : Parcelable {
}