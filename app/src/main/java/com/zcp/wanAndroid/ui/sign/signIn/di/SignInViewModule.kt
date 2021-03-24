package com.zcp.wanAndroid.ui.sign.signIn.di

import com.zcp.wanAndroid.ui.sign.signIn.viewModel.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
class SignInViewModule {

    @Provides
    fun provideSignInViewModel(): SignInViewModel = SignInViewModel()
}