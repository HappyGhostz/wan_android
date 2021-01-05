package com.zcp.wanAndroid.ui.splash.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.splash.SplashActivity
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModel
import com.zcp.wanAndroid.manager.AppViewModelFactory
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Module
import dagger.Provides

@Module
class SplashViewModule(private val activity: SplashActivity) {
    @Provides
    fun getSplashViewModel(appViewModelFactory: AppViewModelFactory): SplashViewModel {
        return ViewModelProvider(activity, appViewModelFactory).get(SplashViewModel::class.java)
    }
}