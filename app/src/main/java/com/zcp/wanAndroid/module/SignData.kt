package com.zcp.wanAndroid.module

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignData(
    val signData: Data?,
    val errorCode: Int,
    val errorMsg: String
): Parcelable

@Parcelize
data class Data(
    val admin: Boolean,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
): Parcelable