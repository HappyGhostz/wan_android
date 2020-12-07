package com.zcp.wanAndroid.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.view.*
import androidx.annotation.RequiresApi
import java.lang.reflect.InvocationTargetException


class FullScreenUtil(window: Window, context: Context) {
    var mWindow = window
    var mContext = context

    /*刘海屏全屏显示FLAG*/
    val FLAG_NOTCH_SUPPORT = 0x00000100 // 开启配置

    val FLAG_NOTCH_PORTRAIT = 0x00000200 // 竖屏配置

    val FLAG_NOTCH_HORIZONTAL = 0x00000400 // 横屏配置

    @RequiresApi(Build.VERSION_CODES.P)
    fun isNotchScreen(): Boolean {
        val decorView = mWindow.decorView
        val windowInsets = decorView.rootWindowInsets
        // 当全屏顶部显示黑边时，getDisplayCutout()返回为null
        val displayCutout = windowInsets?.displayCutout
        return if (displayCutout == null) {
            false
        } else {
            // 获得刘海区域
            val rects: List<Rect> = displayCutout.boundingRects
            rects.isNotEmpty()
        }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun adapterDisplayScreen() {
        var lp = mWindow.attributes
        lp.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        mWindow.attributes = lp
    }

    fun isNotchScreenHuaWei(): Boolean {
        var ret = false
        try {
            val cl: ClassLoader = mContext.getClassLoader()
            val hwNotchSizeUtil =
                cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = hwNotchSizeUtil.getMethod("hasNotchInScreen")
            ret = get.invoke(hwNotchSizeUtil) as Boolean
        } catch (e: ClassNotFoundException) {
        } catch (e: NoSuchMethodException) {
        } catch (e: Exception) {
        } finally {
            return ret
        }
    }

    fun setFullScreenWindowLayoutInDisplayCutoutHuaWei() {
        val layoutParams: WindowManager.LayoutParams = mWindow.attributes
        try {
            val layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx")
            val con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams::class.java)
            val layoutParamsExObj: Any = con.newInstance(layoutParams)
            val method = layoutParamsExCls.getMethod("addHwFlags", Int::class.javaPrimitiveType)
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT)
        } catch (e: ClassNotFoundException) {
        } catch (e: NoSuchMethodException) {
        } catch (e: IllegalAccessException) {
        } catch (e: InstantiationException) {
        } catch (e: InvocationTargetException) {
        } catch (e: java.lang.Exception) {
        }
    }

    fun isNotchScreenXiaoMi(): Boolean {
        var ret = false
        try {
            val cl: ClassLoader = mContext.classLoader
            val systemProperties = cl.loadClass("android.os.SystemProperties")
            val get = systemProperties.getMethod(
                "getInt",
                String::class.java,
                Int::class.javaPrimitiveType
            )
            ret = get.invoke(systemProperties, "ro.miui.notch", 0) as Int == 1
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            return ret
        }

    }

    /**
     * 设置应用窗口在刘海屏手机使用刘海区
     *
     *
     * 通过添加窗口FLAG的方式设置页面使用刘海区显示
     *
     * @param window 应用页面window对象
     */
    fun setFullScreenWindowLayoutInDisplayCutoutXiaoMi() {
        // 竖屏绘制到耳朵区
        val flag: Int = FLAG_NOTCH_SUPPORT or FLAG_NOTCH_PORTRAIT
        try {
            val method = Window::class.java.getMethod(
                "addExtraFlags",
                Int::class.javaPrimitiveType
            )
            method.invoke(mWindow, flag)
        } catch (e: java.lang.Exception) {
        }
    }

    val VIVO_NOTCH = 0x00000020 // 是否有刘海

    val VIVO_FILLET = 0x00000008 // 是否有圆角


    /**
     * 判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    fun hasNotchVivo(): Boolean {
        var ret = false
        try {
            val classLoader = mContext.classLoader
            val ftFeature = classLoader.loadClass("android.util.FtFeature")
            val method =
                ftFeature.getMethod("isFeatureSupport", Int::class.javaPrimitiveType)
            ret = method.invoke(ftFeature, VIVO_NOTCH) as Boolean
        } catch (e: ClassNotFoundException) {
        } catch (e: NoSuchMethodException) {
        } catch (e: java.lang.Exception) {
        } finally {
            return ret
        }
    }

    /**
     * 判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    fun hasNotchInOppo(): Boolean {
        return mContext.packageManager
            .hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }

    @Suppress("DEPRECATION")
    fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mWindow.setDecorFitsSystemWindows(false)
            mWindow.insetsController?.hide(WindowInsets.Type.systemBars())
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            mWindow.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        } else {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (isNotchScreen()) {
                adapterDisplayScreen()
            }
        } else {
            val manufacturer = Build.MANUFACTURER
            when (manufacturer) {
                "HUAWEI" -> {
                    if (isNotchScreenHuaWei()) {
                        setFullScreenWindowLayoutInDisplayCutoutHuaWei()
                    }
                }
                "xiaomi" -> {
                    if (isNotchScreenXiaoMi()) {
                        setFullScreenWindowLayoutInDisplayCutoutXiaoMi()
                    }
                }
            }
        }
    }

    //沉浸模式
    @Suppress("DEPRECATION")
    fun setImmersiveScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mWindow.setDecorFitsSystemWindows(false)
            mWindow.insetsController?.let {
                mWindow.navigationBarColor = Color.TRANSPARENT
                it.show(WindowInsets.Type.systemBars())
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWindow.run {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
            }
        } else {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}