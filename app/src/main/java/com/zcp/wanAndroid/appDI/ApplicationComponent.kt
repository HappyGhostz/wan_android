package com.zcp.wanAndroid.appDI

import android.content.Context
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun getContext(): Context
    fun getRxBus(): ResourcesProvider
}