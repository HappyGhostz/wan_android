package com.zcp.wanAndroid.ui.home

import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseActivity
import com.zcp.wanAndroid.databinding.ActivityHomeBinding
import com.zcp.wanAndroid.ui.home.di.DaggerHomeActivityComponent
import com.zcp.wanAndroid.ui.home.di.HomeViewModule
import com.zcp.wanAndroid.ui.home.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

	@Inject
	lateinit var homeViewModel: HomeViewModel

	override fun getLayoutResource(): Int = R.layout.activity_home

	override fun initInjector() {
		DaggerHomeActivityComponent.builder()
			.applicationComponent(getAppComponent())
			.homeViewModule(HomeViewModule(this))
			.build()
			.inject(this)
	}

	override fun initView() {
		binding.vm = homeViewModel
		initActionBar(binding.toolBar,"首页",false)
	}

}