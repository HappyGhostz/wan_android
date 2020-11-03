package com.zcp.wan_android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zcp.wan_android.utils.FullScreenUtil

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var bindWrapper: BindingWrapper<T>
    val binding: T
        get() = bindWrapper.binding
    open var customImmersive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomImmersive()
        bindWrapper = BindingWrapper(
            DataBindingUtil.setContentView(this,getLayoutResource())
        )
        initInjector()
    }

    abstract fun getLayoutResource(): Int

    abstract fun initInjector()

    fun setCustomImmersive() {
        var fullScreenUtil = FullScreenUtil(window, context = this)
        if (customImmersive) {
            fullScreenUtil.setImmersiveScreen()
        } else {
            fullScreenUtil.setFullScreen()
        }
    }
}


/**
 * why need this Wrapper https://youtrack.jetbrains.com/issue/KT-19080
 */
class BindingWrapper<out T> constructor(val binding: T)