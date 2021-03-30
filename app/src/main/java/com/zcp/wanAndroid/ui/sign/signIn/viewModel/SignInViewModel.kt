package com.zcp.wanAndroid.ui.sign.signIn.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zcp.wanAndroid.Cookies
import com.zcp.wanAndroid.ui.sign.SignInAndUpRepository
import com.zcp.wanAndroid.ui.sign.viewmodel.SignPageData
import com.zcp.wanAndroid.utils.ResponseLoadStatus
import com.zcp.wanAndroid.utils.WanAndroidDataStoreConstants.PASS_WORD
import com.zcp.wanAndroid.utils.WanAndroidDataStoreConstants.USER_NAME
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignInViewModel : ViewModel() {

    var dataStore: DataStore<Preferences>? = null
    var cookies: DataStore<Cookies>? = null
    private var _signInData = MutableLiveData<SignPageData>()
    val signData: LiveData<SignPageData> = _signInData

    private var _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    fun initData() {
        viewModelScope.launch {
            dataStore?.let {
                it.data.map { preferences ->
                    val userName = preferences[USER_NAME] ?: ""
                    val passWord = preferences[PASS_WORD] ?: ""
                    Pair(userName, passWord)
                }
                    .collect { pair ->
                        if (pair.first.isNotEmpty()) {
                            _userName.postValue(pair.first)
                        }
                        if (pair.second.isNotEmpty()) {
                            _password.postValue(pair.second)
                        }
                    }
            }
        }
    }

    fun signInApp(
        signInAndUpRepository: SignInAndUpRepository?,
        userName: String,
        password: String
    ) {
        runBlocking {
            cookies?.let {
                it.updateData { cookies ->
                    if(cookies.cookiePreferencesOrBuilderList.size>0){
                        cookies.toBuilder().clearCookiePreferences().build()
                    }
                    cookies
                }
            }
        }
        signInAndUpRepository?.let { apiRepository ->
            viewModelScope.launch {
                apiRepository.signIn(userName, password)
                    .onStart {
                        _signInData.postValue(SignPageData(null, ResponseLoadStatus.LOADING))
                    }
                    .catch {
                        _signInData.postValue(SignPageData(null, ResponseLoadStatus.ERROR))
                    }
                    .collectLatest {
                        _signInData.postValue(SignPageData(it, ResponseLoadStatus.SUCCEEDED))
                    }
            }
        }
    }

    fun saveOrCleanUser(checked: Boolean, userName: String, passWord: String) {
        var userNameChecked = userName
        var passWordChecked = passWord
        viewModelScope.launch {
            if (!checked) {
                userNameChecked = ""
                passWordChecked = ""
            }
            dataStore?.let {
                it.edit { preferences ->
                    preferences[USER_NAME] = userNameChecked
                    preferences[PASS_WORD] = passWordChecked
                }
            }
        }
    }
}