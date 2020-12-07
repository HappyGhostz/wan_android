package com.zcp.wanAndroid.ui.splash.viewmodel

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zcp.wanAndroid.utils.ResourcesProvider
import com.zcp.wanAndroid.utils.SolarTermsUtil
import com.zcp.wanAndroid.utils.getFamousSentence
import com.zcp.wanAndroid.utils.getSplashImg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*


class SplashViewModel(private val resourcesProvider: ResourcesProvider) : ViewModel() {

    private val solarTermsUtil: SolarTermsUtil by lazy { SolarTermsUtil() }

    var splashImg = ObservableField<Drawable>()
    var splashSentenceOne = ObservableField<String>()
    var splashSentenceTwo = ObservableField<String>()
    var downTimeValue = ObservableField<String>()

    fun initData() {
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
            this.splashImg.set(resourcesProvider.getDrawable(splashImg))
            splashSentenceOne.set(famousSentence[0])
            splashSentenceTwo.set(famousSentence[1])
        }
    }

    fun startDownTime(onOpenNextPage: () -> Unit) {
        viewModelScope.launch {
            flow<Int> {
                (5 downTo 1).forEach {
                    emit(it)
                    delay(1000)
                }
            }.onStart { downTimeValue.set("5") }
                .flowOn(Dispatchers.Default)
                .onCompletion {
                    onOpenNextPage.invoke()
                }
                .catch {
                    onOpenNextPage.invoke()
                }
                .collect {
                    downTimeValue.set("$it")
                }

        }
    }

}