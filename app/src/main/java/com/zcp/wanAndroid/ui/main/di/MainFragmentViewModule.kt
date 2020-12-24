package com.zcp.wanAndroid.ui.main.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.MainFragment
import com.zcp.wanAndroid.ui.main.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainFragmentViewModule(val fragment: MainFragment) {

    @Provides
    fun getMainFragmentModel(): MainViewModel {
        return ViewModelProvider(fragment).get(MainViewModel::class.java)
    }

    @Provides
    fun getLayoutStatusViewModel(): LayoutStatusViewModel {
        return ViewModelProvider(fragment).get(LayoutStatusViewModel::class.java)
    }
}