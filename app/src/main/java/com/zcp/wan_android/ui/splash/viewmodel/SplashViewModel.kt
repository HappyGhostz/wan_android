package com.zcp.wan_android.ui.splash.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.zcp.wan_android.utils.SolarTermsUtil
import com.zcp.wan_android.utils.getFamousSentence
import com.zcp.wan_android.utils.getSplashImg
import java.util.*

class SplashViewModel : ViewModel() {

    val solarTermsUtil: SolarTermsUtil by lazy { SolarTermsUtil() }

    var splashImg = ObservableField<Int>()
    var splashSentenceOne = ObservableField<String>()
    var splashSentenceTwo = ObservableField<String>()

    fun initData() {
        var date = Date(System.currentTimeMillis())
        var day = date.day
        var month = date.month
        var year = date.year
        var name = solarTermsUtil.getSolatName(year, month, day)
        var splashImg = getSplashImg(name)
        var famousSentence = getFamousSentence(name)
        this.splashImg.set(splashImg)
        splashSentenceOne.set(famousSentence[0])
        splashSentenceTwo.set(famousSentence[1])
    }
}