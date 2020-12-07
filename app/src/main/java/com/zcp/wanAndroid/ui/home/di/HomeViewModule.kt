package com.zcp.wanAndroid.ui.home.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.home.HomeActivity
import com.zcp.wanAndroid.ui.home.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class HomeViewModule(val activity: HomeActivity) {
    @Provides
    fun getSplashViewModel(): HomeViewModel {
        return ViewModelProvider(activity).get(HomeViewModel::class.java)
    }
}
