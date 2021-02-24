package com.zcp.wanAndroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.zcp.wanAndroid.WanAndroidApp
import com.zcp.wanAndroid.appDI.ApplicationComponent

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    var mContext: Context? = null

    lateinit var bindWrapper: FragmentBindingWrapper<T>

    val binding: T
        get() = bindWrapper.binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initInject()
        bindWrapper = FragmentBindingWrapper(
            DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        upDataView()
    }

    @LayoutRes
    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(view)
    }


    /**
     * 获取 ApplicationComponent
     *
     * @return ApplicationComponent
     */
    protected fun getAppComponent(): ApplicationComponent? {
        return WanAndroidApp.instance().getAppComponent()
    }

    abstract fun upDataView()

    abstract fun viewCreated(mRootView: View)

    abstract fun initInject()
}

/**
 * why need this Wrapper https://youtrack.jetbrains.com/issue/KT-19080
 */
class FragmentBindingWrapper<out T> constructor(val binding: T)
