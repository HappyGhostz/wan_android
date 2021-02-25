package com.zcp.wanAndroid.ui.sign.di

import com.zcp.wanAndroid.appDI.AppScoped
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.ui.sign.SignInOrSignUpActivity
import dagger.Component

@AppScoped
@Component(dependencies = [ApplicationComponent::class], modules = [SignInOrUpViewModule::class])
interface SignInOrUpComponent {
    fun inject(activity: SignInOrSignUpActivity)
}