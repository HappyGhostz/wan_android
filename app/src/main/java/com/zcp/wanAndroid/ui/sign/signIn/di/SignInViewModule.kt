package com.zcp.wanAndroid.ui.sign.signIn.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.sign.signIn.SignInFragment
import com.zcp.wanAndroid.ui.sign.signIn.viewModel.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
class SignInViewModule(private val signInFragment: SignInFragment) {

    @Provides
    fun provideSignInViewModel(): SignInViewModel {
        return ViewModelProvider(signInFragment).get(SignInViewModel::class.java)
    }
}