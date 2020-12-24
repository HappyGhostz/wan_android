package com.zcp.wanAndroid.ui.home

import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseActivity
import com.zcp.wanAndroid.databinding.ActivityHomeBinding
import com.zcp.wanAndroid.extension.addFragment
import com.zcp.wanAndroid.extension.hideFragment
import com.zcp.wanAndroid.extension.showFragment
import com.zcp.wanAndroid.ui.communication.CommunicationFragment
import com.zcp.wanAndroid.ui.home.di.DaggerHomeActivityComponent
import com.zcp.wanAndroid.ui.home.di.HomeViewModule
import com.zcp.wanAndroid.ui.home.viewmodel.HomeViewModel
import com.zcp.wanAndroid.ui.my.MyFragment
import com.zcp.wanAndroid.ui.navigation.NavigationFragment
import javax.inject.Inject

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

	@Inject
	lateinit var homeViewModel: HomeViewModel
	@Inject
	lateinit var mainFragment: MyFragment
	@Inject
	lateinit var communicationFragment: CommunicationFragment
	@Inject
	lateinit var navigationFragment: NavigationFragment
	@Inject
	lateinit var myFragment: MyFragment

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
		initActionBar(binding.toolBar,homeViewModel.toolbarName.get()!!,false)
		addHomeFragment()
		binding.bottomNavigationView.setOnNavigationItemSelectedListener {
			val id = binding.bottomNavigationView.selectedItemId
			when(it.itemId){
				R.id.menu_main->{
					if (isSameId(id,R.id.menu_main)) return@setOnNavigationItemSelectedListener true
					homeViewModel.upDateToolbarName("首页")
					showFragment(mainFragment)
					hideFragment(communicationFragment)
					hideFragment(navigationFragment)
					hideFragment(myFragment)
				}
				R.id.menu_wechat->{
					if (isSameId(id,R.id.menu_wechat)) return@setOnNavigationItemSelectedListener true
					homeViewModel.upDateToolbarName("公众号")
					val countCommunicationFragment = supportFragmentManager.findFragmentByTag("communication")
					if(countCommunicationFragment==null){
						addFragment(communicationFragment,R.id.fl_container,"communication")
					}else{
						showFragment(communicationFragment)
					}
					hideFragment(mainFragment)
					hideFragment(navigationFragment)
					hideFragment(myFragment)
				}
				R.id.menu_navigation->{
					if (isSameId(id,R.id.menu_navigation)) return@setOnNavigationItemSelectedListener true
					homeViewModel.upDateToolbarName("导航")
					val countNavigationFragment = supportFragmentManager.findFragmentByTag("navigation")
					if(countNavigationFragment==null){
						addFragment(navigationFragment,R.id.fl_container,"navigation")
					}else{
						showFragment(navigationFragment)
					}
					hideFragment(mainFragment)
					hideFragment(communicationFragment)
					hideFragment(myFragment)
				}
				R.id.menu_my->{
					if (isSameId(id,R.id.menu_my)) return@setOnNavigationItemSelectedListener true
					homeViewModel.upDateToolbarName("我的")
					val countMyFragment = supportFragmentManager.findFragmentByTag("my")
					if(countMyFragment==null){
						addFragment(myFragment,R.id.fl_container,"my")
					}else{
						showFragment(myFragment)
					}
					hideFragment(mainFragment)
					hideFragment(communicationFragment)
					hideFragment(navigationFragment)
				}
			}
			return@setOnNavigationItemSelectedListener true
		}
	}

	private fun isSameId(id: Int, itemId: Int): Boolean {
		if (id == itemId) {
			return true
		}
		return false
	}

	private fun addHomeFragment() {
		addFragment(mainFragment,R.id.fl_container,"main")
	}

}