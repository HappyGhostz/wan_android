package com.zcp.wanAndroid.ui.splash.di

import com.zcp.wanAndroid.appDI.AppScoped
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.ui.splash.SplashActivity
import dagger.Component

@AppScoped
@Component(dependencies = [ApplicationComponent::class], modules = [SplashViewModule::class])
interface SplashActivityComponent {
    fun inject(activity: SplashActivity)
}