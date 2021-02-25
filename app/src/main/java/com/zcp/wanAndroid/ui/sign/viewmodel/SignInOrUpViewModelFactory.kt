package com.zcp.wanAndroid.ui.sign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.utils.ResourcesProvider

class SignInOrUpViewModelFactory(private val resourcesProvider: ResourcesProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInOrUpViewModel::class.java) -> {
                SignInOrUpViewModel(resourcesProvider) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    }
}