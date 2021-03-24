package com.zcp.wanAndroid.ui.sign.signIn.di

import com.zcp.wanAndroid.appDI.AppScoped
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.ui.sign.signIn.SignInFragment
import dagger.Component

@AppScoped
@Component(dependencies = [ApplicationComponent::class], modules = [SignInViewModule::class])
interface SignInComponent {
    fun inject(signInFragment: SignInFragment)
}