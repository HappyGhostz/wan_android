package com.zcp.wan_android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zcp.wan_android.utils.FullScreenUtil

abstract class BaseActivity : AppCompatActivity() {

    open var customImmersive = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomImmersive()
        setContentView(getLayoutResource())
    }

    abstract fun getLayoutResource(): Int

    fun setCustomImmersive() {
        var fullScreenUtil = FullScreenUtil(window, context = this)
        if (customImmersive) {
            fullScreenUtil.setImmersiveScreen()
        } else {
            fullScreenUtil.setFullScreen()
        }
    }
}