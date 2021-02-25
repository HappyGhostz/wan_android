package com.zcp.wanAndroid.ui.sign.di

import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.sign.SignInOrSignUpActivity
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModel
import dagger.Module
import dagger.Provides

@Module
class SignInOrUpViewModule(private val activity: SignInOrSignUpActivity) {

    @Provides
    fun providesSignInOrUpViewModel(): SignInOrUpViewModel {
        return ViewModelProvider(activity).get(SignInOrUpViewModel::class.java)
    }
}