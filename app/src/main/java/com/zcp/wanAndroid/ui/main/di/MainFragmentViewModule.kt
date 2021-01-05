package com.zcp.wanAndroid.ui.main.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.manager.AppViewModelFactory
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.MainFragment
import com.zcp.wanAndroid.ui.main.viewmodel.MainViewModel
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides

@Module
class MainFragmentViewModule(private val fragment: MainFragment) {

    @Provides
    fun getMainFragmentModel(appViewModelFactory: AppViewModelFactory): MainViewModel {
        return ViewModelProvider(fragment, appViewModelFactory).get(MainViewModel::class.java)
    }

    @Provides
    fun getLayoutStatusViewModel(): LayoutStatusViewModel {
        return ViewModelProvider(fragment).get(LayoutStatusViewModel::class.java)
    }
}