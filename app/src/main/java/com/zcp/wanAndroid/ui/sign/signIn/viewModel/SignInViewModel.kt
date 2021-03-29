package com.zcp.wanAndroid.ui.sign.signIn.viewModel

import android.os.Parcelable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zcp.wanAndroid.module.SignInData
import com.zcp.wanAndroid.ui.sign.SignInAndUpRepository
import com.zcp.wanAndroid.utils.ResponseLoadStatus
import com.zcp.wanAndroid.utils.WanAndroidDataStoreConstants.PASS_WORD
import com.zcp.wanAndroid.utils.WanAndroidDataStoreConstants.USER_NAME
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Parcelize
data class SignInPageData(
    val signInData: SignInData? = null,
    val loadingStatus: ResponseLoadStatus
) : Parcelable {}

class SignInViewModel : ViewModel() {

    var dataStore: DataStore<Preferences>? = null
    private var _signInData = MutableLiveData<SignInPageData>()
    val signInData: LiveData<SignInPageData> = _signInData

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
        signInAndUpRepository?.let { apiRepository ->
            viewModelScope.launch {
                apiRepository.signIn(userName, password)
                    .onStart {
                        _signInData.postValue(SignInPageData(null, ResponseLoadStatus.LOADING))
                    }
                    .catch {
                        _signInData.postValue(SignInPageData(null, ResponseLoadStatus.ERROR))
                    }
                    .collectLatest {
                        _signInData.postValue(SignInPageData(it, ResponseLoadStatus.SUCCESSED))
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