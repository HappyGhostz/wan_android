package com.zcp.wanAndroid.ui.sign.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.sign.SignInOrSignUpActivity
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModel
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModelFactory
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModel
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModelFactory
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Module
import dagger.Provides

@Module
class SignInOrUpViewModule(private val activity: SignInOrSignUpActivity) {

    @Provides
    fun providesSignInOrUpViewModel(signInOrUpViewModelFactory: SignInOrUpViewModelFactory): SignInOrUpViewModel {
        return ViewModelProvider(
            activity,
            signInOrUpViewModelFactory
        ).get(SignInOrUpViewModel::class.java)
    }

    @Provides
    fun provideSignInOrUpViewModelFactory(resourcesProvider: ResourcesProvider): SignInOrUpViewModelFactory {
        return SignInOrUpViewModelFactory(resourcesProvider)
    }
}