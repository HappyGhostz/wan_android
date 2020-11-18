package com.zcp.wanAndroid.ui.splash

import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseActivity
import com.zcp.wanAndroid.databinding.ActivitySplashBinding
import com.zcp.wanAndroid.ui.splash.di.DaggerSplashActivityComponent
import com.zcp.wanAndroid.ui.splash.di.SplashViewModule
import com.zcp.wanAndroid.ui.splash.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    @Inject
    lateinit var splashViewModel: SplashViewModel

    override var customImmersive = false
    override fun getLayoutResource(): Int = R.layout.activity_splash
    override fun initInjector() {
        DaggerSplashActivityComponent.builder()
            .applicationComponent(getAppComponent())
            .splashViewModule(SplashViewModule(this))
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.vm = splashViewModel
        splashViewModel.initData()
    }
}