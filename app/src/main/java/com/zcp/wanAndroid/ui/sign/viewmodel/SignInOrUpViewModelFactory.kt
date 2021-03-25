package com.zcp.wanAndroid.ui.sign.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.utils.ResourcesProvider

class SignInOrUpViewModelFactory(private val resourcesProvider: ResourcesProvider, private var dataStore: DataStore<Preferences>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInOrUpViewModel::class.java) -> {
                SignInOrUpViewModel(resourcesProvider, dataStore) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    }
}