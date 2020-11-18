package com.zcp.wanAndroid.ui.splash.viewmodel

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.zcp.wanAndroid.utils.*
import java.util.*

class SplashViewModel(val resourcesProvider: ResourcesProvider) : ViewModel() {

    val solarTermsUtil: SolarTermsUtil by lazy { SolarTermsUtil() }

    var splashImg = ObservableField<Drawable>()
    var splashSentenceOne = ObservableField<String>()
    var splashSentenceTwo = ObservableField<String>()

    fun initData() {
        Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }.let {
            var name = solarTermsUtil.getSolatName(it.get(Calendar.YEAR), it.get(Calendar.MONTH)+1, it.get(Calendar.DATE))
            var splashImg = getSplashImg(name)
            var famousSentence = getFamousSentence(name)
            this.splashImg.set(resourcesProvider.getDrawable(splashImg))
            splashSentenceOne.set(famousSentence[0])
            splashSentenceTwo.set(famousSentence[1])
        }
    }
}