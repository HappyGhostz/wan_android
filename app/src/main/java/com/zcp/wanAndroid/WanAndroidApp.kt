package com.zcp.wanAndroid

import android.app.Application
import com.zcp.wanAndroid.appDI.ApplicationComponent
import com.zcp.wanAndroid.appDI.ApplicationModule
import com.zcp.wanAndroid.appDI.DaggerApplicationComponent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WanAndroidApp: Application() {
    private var sAppComponent: ApplicationComponent? = null


    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }

    private fun init() {
        initInject()
    }

    private fun initInject() {
        // 这里不做注入操作，只提供一些全局单例数据
        sAppComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this@WanAndroidApp))
            .build()
    }

    fun getAppComponent(): ApplicationComponent? {
        return sAppComponent
    }

    //单例化的第三种方式：自定义一个非空且只能一次性赋值的委托属性
    companion object {
        private var instance: WanAndroidApp by NotNullSingleValueVar()
        fun instance() = instance
    }

    //定义一个属性管理类，进行非空和重复赋值的判断
    private class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
        private var value: T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("application not initialized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value
            else throw IllegalStateException("application already initialized")
        }
    }

}