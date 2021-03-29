package com.zcp.wanAndroid.ui.sign.signUp.di

import com.zcp.wanAndroid.appDI.AppScoped
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.ui.sign.signUp.SignUpFragment
import dagger.Component

@AppScoped
@Component(dependencies = [ApplicationComponent::class], modules = [SignUpViewModule::class])
interface SignUpComponent {
    fun inject(signUpFragment: SignUpFragment)
}