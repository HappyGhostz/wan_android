package com.zcp.wanAndroid.ui.sign.signUp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.sign.signUp.SignUpFragment
import com.zcp.wanAndroid.ui.sign.signUp.viewModel.SignUpViewModel
import dagger.Module
import dagger.Provides

@Module
class SignUpViewModule(private val signUpFragment: SignUpFragment) {

    @Provides
    fun provideSignUpViewModel(dataStore: DataStore<Preferences>): SignUpViewModel {
        return ViewModelProvider(signUpFragment).get(SignUpViewModel::class.java).apply {
            this.dataStore = dataStore
        }
    }

}