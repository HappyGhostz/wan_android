package com.zcp.wanAndroid.ui.layoutStatusViewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class LayoutStatusViewModel:ViewModel() {
    var isShowErrorPage = ObservableField<Boolean>()
    var isShowEmptyPage = ObservableField<Boolean>()
    var isShowLoadingPage = ObservableField<Boolean>()

    init {
        isShowErrorPage.set(false)
        isShowEmptyPage.set(false)
        isShowLoadingPage.set(true)
    }

    fun errorReloadClick(){

    }

    fun emptyReloadClick(){

    }
}