package com.zcp.wanAndroid.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.utils.ResourcesProvider

class SplashViewModelFactory(private val resourcesProvider: ResourcesProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(resourcesProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}