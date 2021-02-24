package com.zcp.wanAndroid.ui.splash.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.splash.SplashActivity
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModel
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModelFactory
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Module
import dagger.Provides

@Module
class SplashViewModule(private val activity: SplashActivity) {
    @Provides
    fun provideSplashViewModel(splashViewModelFactory: SplashViewModelFactory): SplashViewModel {
        return ViewModelProvider(activity, splashViewModelFactory).get(SplashViewModel::class.java)
    }

    @Provides
    fun provideSplashViewModuleFactory(resourcesProvider: ResourcesProvider): SplashViewModelFactory {
        return SplashViewModelFactory(resourcesProvider)
    }
}