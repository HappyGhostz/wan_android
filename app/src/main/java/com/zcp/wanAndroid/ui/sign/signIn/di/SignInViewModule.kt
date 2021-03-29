package com.zcp.wanAndroid.ui.sign.signIn.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.sign.signIn.SignInFragment
import com.zcp.wanAndroid.ui.sign.signIn.viewModel.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
class SignInViewModule(private val signInFragment: SignInFragment) {

    @Provides
    fun provideSignInViewModel(dataStore: DataStore<Preferences>): SignInViewModel {
        return ViewModelProvider(signInFragment).get(SignInViewModel::class.java).apply {
            this.dataStore = dataStore
        }
    }
}