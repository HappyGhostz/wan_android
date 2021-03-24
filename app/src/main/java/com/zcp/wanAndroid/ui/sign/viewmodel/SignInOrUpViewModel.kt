package com.zcp.wanAndroid.ui.sign.viewmodel

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.zcp.wanAndroid.utils.ResourcesProvider
import com.zcp.wanAndroid.utils.SolarTermsUtil
import com.zcp.wanAndroid.utils.getFamousSentence
import com.zcp.wanAndroid.utils.getSplashImg
import java.util.*

class SignInOrUpViewModel(private val resourcesProvider: ResourcesProvider) : ViewModel() {

    private val solarTermsUtil: SolarTermsUtil by lazy { SolarTermsUtil() }
    var circularImg = ObservableField<Drawable>()
    var splashSentenceOne = ObservableField<String>("")
    var splashSentenceTwo = ObservableField<String>("")
    var isShowPassWord = ObservableField<Boolean>(false)

    var showingSignUpFragment: Boolean = false

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
}