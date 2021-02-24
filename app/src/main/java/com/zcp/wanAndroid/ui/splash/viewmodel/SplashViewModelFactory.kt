package com.zcp.wanAndroid.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.utils.ResourcesProvider

class SplashViewModelFactory(private val resourcesProvider: ResourcesProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(resourcesProvider) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    }
}