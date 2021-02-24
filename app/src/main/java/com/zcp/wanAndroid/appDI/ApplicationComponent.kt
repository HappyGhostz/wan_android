package com.zcp.wanAndroid.appDI

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zcp.wanAndroid.utils.ImageLoadUtils
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun getContext(): Context
    fun getResources(): ResourcesProvider
    fun getRetrofit(): Retrofit
    fun getAppDataStore(): DataStore<Preferences>
    fun getImageLoadUtils(): ImageLoadUtils
}