package com.zcp.wanAndroid.appDI

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.zcp.wanAndroid.CookiePreferences
import com.zcp.wanAndroid.Cookies
import com.zcp.wanAndroid.WanAndroidApp
import com.zcp.wanAndroid.manager.AppViewModelFactory
import com.zcp.wanAndroid.manager.CookieJarImpl
import com.zcp.wanAndroid.manager.cookieManager.CookieManagerImpl
import com.zcp.wanAndroid.manager.cookieManager.CookieSerializable
import com.zcp.wanAndroid.manager.cookieManager.CookiesSerializable
import com.zcp.wanAndroid.net.NetPath
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val appApplication: WanAndroidApp) {

    @Singleton
    @Provides
    internal fun provideWanAndroidAppContext(): Context = appApplication

    @Singleton
    @Provides
    internal fun provideResourcesProvider(): ResourcesProvider =
        ResourcesProvider(appApplication.resources)

    @Singleton
    @Provides
    internal fun provideRetrofit(cookieJarImpl: CookieJarImpl): Retrofit {
        val cache = Cache(File(appApplication.cacheDir, "HttpCache"), 1024 * 1024 * 1024)
        val okHttpClient = OkHttpClient.Builder().cache(cache)
            .retryOnConnectionFailure(true)
            .cookieJar(cookieJarImpl)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder().baseUrl(NetPath.APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getAppModelFactory(resourcesProvider: ResourcesProvider,retrofit: Retrofit): AppViewModelFactory =
        AppViewModelFactory(resourcesProvider,retrofit)

    @Singleton
    @Provides
    internal fun provideCookieJarImpl(cookieManagerImpl: CookieManagerImpl): CookieJarImpl =
        CookieJarImpl(cookieManagerImpl)

    @Singleton
    @Provides
    internal fun provideCookieManagerImpl(cookiesPreferences: DataStore<Cookies>): CookieManagerImpl =
        CookieManagerImpl(cookiesPreferences)

    @Singleton
    @Provides
    internal fun providePreferencesDataStore(context: Context): DataStore<Preferences> =
        appApplication.createDataStore(name = "wanAndroid")

    @Singleton
    @Provides
    internal fun provideCookieProtoDataStore(context: Context): DataStore<CookiePreferences> =
        appApplication.createDataStore(fileName = "cookie", serializer = CookieSerializable)

    @Singleton
    @Provides
    internal fun provideCookiesProtoDataStore(context: Context): DataStore<Cookies> =
        appApplication.createDataStore(fileName = "cookies", serializer = CookiesSerializable)
}