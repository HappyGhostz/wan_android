package com.zcp.wanAndroid.appDI

import android.content.Context
import com.zcp.wanAndroid.WanAndroidApp
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val appApplication: WanAndroidApp) {

    @Singleton
    @Provides
    internal fun provideWanAndroidAppContext(): Context = appApplication

    @Singleton
    @Provides
    internal fun provideResourcesProvider(): ResourcesProvider =
        ResourcesProvider(appApplication.resources)
}