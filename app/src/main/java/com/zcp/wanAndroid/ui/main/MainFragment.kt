package com.zcp.wanAndroid.ui.main

import com.zcp.wanAndroid.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zcp.wanAndroid.base.BaseFragment
import com.zcp.wanAndroid.databinding.FragmentMainBinding
import com.zcp.wanAndroid.ui.home.viewmodel.HomeViewModel
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.di.DaggerMainFragmentComponent
import com.zcp.wanAndroid.ui.main.di.MainFragmentViewModule
import com.zcp.wanAndroid.ui.main.viewmodel.MainViewModel
import javax.inject.Inject


class MainFragment : BaseFragment<FragmentMainBinding>() {
    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var layoutStatusViewModel: LayoutStatusViewModel

    override fun upDataView() {
    }

    override fun viewCreated(mRootView: View) {
        binding.vm = mainViewModel
        binding.loadingViewModel = layoutStatusViewModel
        binding.mainList.rvList.adapter =
    }

    override fun initInject() {
        DaggerMainFragmentComponent.builder()
            .applicationComponent(getAppComponent())
            .mainFragmentViewModule(MainFragmentViewModule(this))
            .build()
            .inject(this)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_main
}