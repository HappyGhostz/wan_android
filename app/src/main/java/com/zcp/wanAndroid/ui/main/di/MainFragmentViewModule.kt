package com.zcp.wanAndroid.ui.main.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.MainFragment
import com.zcp.wanAndroid.ui.main.adapter.MainListAdapter
import com.zcp.wanAndroid.ui.main.viewmodel.MainViewModel
import com.zcp.wanAndroid.ui.main.viewmodel.MainViewModelFactory
import com.zcp.wanAndroid.utils.ImageLoadUtils
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainFragmentViewModule(private val fragment: MainFragment) {

    @Provides
    fun provideMainFragmentModel(mainViewModelFactory: MainViewModelFactory): MainViewModel {
        return ViewModelProvider(fragment, mainViewModelFactory).get(MainViewModel::class.java)
    }

    @Provides
    fun provideMainViewModelFactory(
        retrofit: Retrofit,
        layoutStatusViewModel: LayoutStatusViewModel
    ): MainViewModelFactory {
        return MainViewModelFactory(retrofit, layoutStatusViewModel)
    }

    @Provides
    fun provideLayoutStatusViewModel(): LayoutStatusViewModel {
        return ViewModelProvider(fragment).get(LayoutStatusViewModel::class.java)
    }

    @Provides
    fun provideMainListAdapter(resourcesProvider: ResourcesProvider): MainListAdapter {
        return MainListAdapter(fragment.requireActivity(), resourcesProvider)
    }
}