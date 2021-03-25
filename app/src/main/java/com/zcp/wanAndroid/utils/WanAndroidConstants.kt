package com.zcp.wanAndroid.utils

import androidx.datastore.preferences.core.preferencesKey

object WanAndroidDataStoreConstants {
    val IS_SHOW_USER_PASS_WORD = preferencesKey<Boolean> ("isShowPassword")
    val IS_REMEMBER_PASS_WORD = preferencesKey<Boolean> ("isRememberPassword")
}