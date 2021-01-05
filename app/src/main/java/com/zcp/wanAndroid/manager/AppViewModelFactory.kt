package com.zcp.wanAndroid.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.main.viewmodel.MainViewModel
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModel
import com.zcp.wanAndroid.utils.ResourcesProvider
import retrofit2.Retrofit

class AppViewModelFactory(
    private val resourcesProvider: ResourcesProvider,
    private val retrofit: Retrofit
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(resourcesProvider) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(retrofit) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    }
}