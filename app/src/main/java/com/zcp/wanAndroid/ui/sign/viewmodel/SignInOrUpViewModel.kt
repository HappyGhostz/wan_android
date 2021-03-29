package com.zcp.wanAndroid.ui.sign.viewmodel

import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.databinding.ObservableField
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zcp.wanAndroid.module.SignData
import com.zcp.wanAndroid.utils.*
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

@Parcelize
data class SignPageData(
    val signData: SignData? = null,
    val loadingStatus: ResponseLoadStatus
) : Parcelable {}

class SignInOrUpViewModel(private val resourcesProvider: ResourcesProvider, private var dataStore: DataStore<Preferences>) : ViewModel() {

    private val solarTermsUtil: SolarTermsUtil by lazy { SolarTermsUtil() }
    var circularImg = ObservableField<Drawable>()
    var splashSentenceOne = ObservableField<String>("")
    var splashSentenceTwo = ObservableField<String>("")

    var showingSignUpFragment: Boolean = false

    var isShowUsedPassword = MutableLiveData<Boolean>(false)
    var isRememberUserPassword = MutableLiveData<Boolean>(false)

    fun initData(){
        val calendar = Calendar.getInstance()
        calendar.apply {
            timeInMillis = System.currentTimeMillis()
        }.let {
            val name = solarTermsUtil.getSolatName(
                it.get(Calendar.YEAR), it.get(Calendar.MONTH) + 1, it.get(
                    Calendar.DATE
                )
            )
            val splashImg = getSplashImg(name)
            val famousSentence = getFamousSentence(name)
            this.circularImg.set(resourcesProvider.getDrawable(splashImg))
            splashSentenceOne.set(famousSentence[0])
            splashSentenceTwo.set(famousSentence[1])
        }
    }

    fun initShowPasswordValue() {
        viewModelScope.launch {
            dataStore.data.map { preferences ->
                val isShowPassword = preferences[WanAndroidDataStoreConstants.IS_SHOW_USER_PASS_WORD] ?: false
                val isRememberPassword = preferences[WanAndroidDataStoreConstants.IS_REMEMBER_PASS_WORD] ?: false
                Pair(isShowPassword, isRememberPassword)
            }.collect {pair->
                postIsShowUsedPasswordValue(pair.first)
                postIsRememberPasswordValue(pair.second)
            }
        }

    }

    fun postIsShowUsedPasswordValue(isShowPassword: Boolean) {
        isShowUsedPassword.postValue(isShowPassword)
    }

    fun postIsRememberPasswordValue(isRememberPassword: Boolean) {
        isRememberUserPassword.postValue(isRememberPassword)
    }

    fun saveIsShowUsedPasswordValue(isShowPassword: Boolean){
        viewModelScope.launch {
            dataStore.edit {preferences ->
                preferences[WanAndroidDataStoreConstants.IS_SHOW_USER_PASS_WORD] = isShowPassword
            }
        }
    }

    fun saveIsRememberPasswordValue(isRememberPassword: Boolean){
        viewModelScope.launch {
            dataStore.edit {preferences ->
                preferences[WanAndroidDataStoreConstants.IS_REMEMBER_PASS_WORD] = isRememberPassword
            }
        }
    }
}