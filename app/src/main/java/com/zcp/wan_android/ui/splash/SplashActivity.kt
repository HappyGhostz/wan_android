package com.zcp.wan_android.ui.splash

import com.zcp.wan_android.R
import com.zcp.wan_android.base.BaseActivity
import com.zcp.wan_android.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override var customImmersive  = false
    override fun getLayoutResource(): Int = R.layout.activity_splash
}