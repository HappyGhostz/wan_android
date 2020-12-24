package com.zcp.wanAndroid.ui.main.di

import com.zcp.wanAndroid.appDI.AppScoped
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.ui.main.MainFragment
import dagger.Component

@AppScoped
@Component(dependencies = [ApplicationComponent::class], modules = [MainFragmentViewModule::class])
interface MainFragmentComponent {
    fun inject(mainFragment: MainFragment)
}