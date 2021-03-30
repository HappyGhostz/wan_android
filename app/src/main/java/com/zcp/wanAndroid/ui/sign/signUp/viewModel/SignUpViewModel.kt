package com.zcp.wanAndroid.ui.sign.signUp.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zcp.wanAndroid.module.SignData
import com.zcp.wanAndroid.ui.sign.SignInAndUpRepository
import com.zcp.wanAndroid.ui.sign.viewmodel.SignPageData
import com.zcp.wanAndroid.utils.ResponseLoadStatus
import com.zcp.wanAndroid.utils.WanAndroidDataStoreConstants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    var dataStore: DataStore<Preferences>? = null
    private var _signUnData = MutableLiveData<SignPageData>()
    val signUpData: LiveData<SignPageData> = _signUnData

    fun signUpApp(
        signInAndUpRepository: SignInAndUpRepository?,
        username: String,
        password: String,
        rePassword: String
    ) {

        if (password != rePassword) {
            _signUnData.postValue(
                SignPageData(
                    SignData(null, -1, "密码不一致!"),
                    ResponseLoadStatus.SUCCEEDED
                )
            )
            return
        }

        signInAndUpRepository?.let { apiRepository ->
            viewModelScope.launch {
                apiRepository.signUp(username, password, rePassword)
                    .onStart {
                        _signUnData.postValue(SignPageData(null, ResponseLoadStatus.LOADING))
                    }
                    .catch {
                        _signUnData.postValue(SignPageData(null, ResponseLoadStatus.ERROR))
                    }
                    .collectLatest {
                        _signUnData.postValue(SignPageData(it, ResponseLoadStatus.SUCCEEDED))
                    }
            }
        }
    }

    fun saveUser(checked: Boolean, userName: String, password: String) {
        viewModelScope.launch {
            if (checked) {
                dataStore?.let {
                    it.edit { preferences ->
                        preferences[WanAndroidDataStoreConstants.USER_NAME] = userName
                        preferences[WanAndroidDataStoreConstants.PASS_WORD] = password
                    }
                }
            }
        }
    }
}