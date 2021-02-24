package com.zcp.wanAndroid.ui.layoutStatusViewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class LayoutStatusViewModel:ViewModel() {
    var isShowErrorPage = ObservableField<Boolean>(false)
    var isShowEmptyPage = ObservableField<Boolean>(false)
    var isShowLoadingPage = ObservableField<Boolean>(true)

    fun errorReloadClick(){

    }

    fun emptyReloadClick(){

    }

    fun showLoadingPage(){
        isShowLoadingPage.set(true)
        isShowErrorPage.set(false)
        isShowEmptyPage.set(false)
    }

    fun showErrorPage(){
        isShowLoadingPage.set(false)
        isShowErrorPage.set(true)
        isShowEmptyPage.set(false)
    }

    fun showEmptyPage(){
        isShowLoadingPage.set(false)
        isShowErrorPage.set(true)
        isShowEmptyPage.set(false)
    }

    fun hideAllPage(){
        isShowLoadingPage.set(false)
        isShowErrorPage.set(false)
        isShowEmptyPage.set(false)
    }
}