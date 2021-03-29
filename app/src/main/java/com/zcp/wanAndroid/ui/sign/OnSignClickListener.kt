package com.zcp.wanAndroid.ui.sign

import com.zcp.wanAndroid.ui.sign.viewmodel.SignPageData

interface OnSignClickListener {
    fun onSignInClickListener(signPageData: SignPageData)
    fun onSignUpClickListener(signPageData: SignPageData)
}