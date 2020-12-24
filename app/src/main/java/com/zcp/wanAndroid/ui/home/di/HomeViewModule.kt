package com.zcp.wanAndroid.ui.home.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.communication.CommunicationFragment
import com.zcp.wanAndroid.ui.home.HomeActivity
import com.zcp.wanAndroid.ui.home.viewmodel.HomeViewModel
import com.zcp.wanAndroid.ui.main.MainFragment
import com.zcp.wanAndroid.ui.my.MyFragment
import com.zcp.wanAndroid.ui.navigation.NavigationFragment
import dagger.Module
import dagger.Provides

@Module
class HomeViewModule(val activity: HomeActivity) {
    @Provides
    fun getSplashViewModel(): HomeViewModel {
        return ViewModelProvider(activity).get(HomeViewModel::class.java)
    }
    @Provides
    fun getMainFragment(): MainFragment {
        return MainFragment.newInstance()
    }
    @Provides
    fun getCommunicationFragment(): CommunicationFragment {
        return CommunicationFragment.newInstance()
    }
    @Provides
    fun getNavigationFragment(): NavigationFragment {
        return NavigationFragment.newInstance()
    }
    @Provides
    fun getMyFragment(): MyFragment {
        return MyFragment.newInstance()
    }
}
