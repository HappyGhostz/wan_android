package com.zcp.wanAndroid.ui.home.di

import com.zcp.wanAndroid.appDI.AppScoped
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.ui.home.HomeActivity
import dagger.Component

@AppScoped
@Component(dependencies = [ApplicationComponent::class], modules = [HomeViewModule::class])
interface HomeActivityComponent {
    fun inject(activity: HomeActivity)
}