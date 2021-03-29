package com.zcp.wanAndroid.ui.sign.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.sign.SignInAndUpRepository
import com.zcp.wanAndroid.ui.sign.SignInOrSignUpActivity
import com.zcp.wanAndroid.ui.sign.apiService.SignInAndUpService
import com.zcp.wanAndroid.ui.sign.signIn.SignInFragment
import com.zcp.wanAndroid.ui.sign.signUp.SignUpFragment
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModel
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModelFactory
import com.zcp.wanAndroid.utils.DialogUtils
import com.zcp.wanAndroid.utils.ResourcesProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SignInOrUpViewModule(private val activity: SignInOrSignUpActivity) {

    @Provides
    fun providesSignInOrUpViewModel(signInOrUpViewModelFactory: SignInOrUpViewModelFactory): SignInOrUpViewModel {
        return ViewModelProvider(
            activity,
            signInOrUpViewModelFactory
        ).get(SignInOrUpViewModel::class.java)
    }

    @Provides
    fun provideSignInOrUpViewModelFactory(
        resourcesProvider: ResourcesProvider,
        dataStore: DataStore<Preferences>
    ): SignInOrUpViewModelFactory {
        return SignInOrUpViewModelFactory(resourcesProvider, dataStore)
    }

    @Provides
    fun provideSignInFragment(signInAndUpRepository: SignInAndUpRepository): SignInFragment =
        SignInFragment().apply {
            this.signInAndUpRepository = signInAndUpRepository
        }

    @Provides
    fun provideSignUpFragment(signInAndUpRepository: SignInAndUpRepository): SignUpFragment = SignUpFragment().apply {
        this.signInAndUpRepository = signInAndUpRepository
    }

    @Provides
    fun provideSignInAndUpRepository(retrofit: Retrofit): SignInAndUpRepository {
        val api = retrofit.create(SignInAndUpService::class.java)
        return SignInAndUpRepository(api)
    }

    @Provides
    fun provideDialogUtils(res: ResourcesProvider): DialogUtils {
        return DialogUtils(res)
    }

}