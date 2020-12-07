package com.zcp.wanAndroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zcp.wanAndroid.WanAndroidApp
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.utils.FullScreenUtil

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var bindWrapper: BindingWrapper<T>
    val binding: T
        get() = bindWrapper.binding
    open var customImmersive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomImmersive()
        bindWrapper = BindingWrapper(
            DataBindingUtil.setContentView(this, getLayoutResource())
        )
        initInjector()
        initView()
    }

    abstract fun getLayoutResource(): Int

    abstract fun initInjector()

    abstract fun initView()

    private fun setCustomImmersive() {
        val fullScreenUtil = FullScreenUtil(window, context = this)
        if (customImmersive) {
            fullScreenUtil.setImmersiveScreen()
        } else {
            fullScreenUtil.setFullScreen()
        }
    }

    open fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    open fun px2dp(pxVal: Float): Float {
        val scale: Float = resources.displayMetrics.density
        return pxVal / scale
    }


    fun initActionBar(toolbar: Toolbar, string: String, isShowHomeEnable: Boolean = false){
        toolbar.title=string
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isShowHomeEnable)
    }

    protected fun getAppComponent(): ApplicationComponent? {
        return WanAndroidApp.instance().getAppComponent()
    }

}


/**
 * why need this Wrapper https://youtrack.jetbrains.com/issue/KT-19080
 */
class BindingWrapper<out T> constructor(val binding: T)