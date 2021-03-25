package com.zcp.wanAndroid.ui.sign.signIn.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    var dataStore: DataStore<Preferences>? = null

    var isShowUsedPassword = MutableLiveData<Boolean>(false)
    var isRememberUserPassword = MutableLiveData<Boolean>(false)

    fun initData() {
    }
}