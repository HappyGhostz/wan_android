package com.zcp.wanAndroid.ui.sign

import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseActivity
import com.zcp.wanAndroid.databinding.ActivitySignBinding
import com.zcp.wanAndroid.ui.sign.di.DaggerSignInOrUpComponent
import com.zcp.wanAndroid.ui.sign.di.SignInOrUpViewModule
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModel
import javax.inject.Inject

class SignInOrSignUpActivity : BaseActivity<ActivitySignBinding>() {

    @Inject
    lateinit var signInOrUpViewModel: SignInOrUpViewModel


    override fun getLayoutResource(): Int = R.layout.activity_sign

    override fun initInjector() {
        DaggerSignInOrUpComponent.builder()
            .applicationComponent(getAppComponent())
            .signInOrUpViewModule(SignInOrUpViewModule(this))
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.vm = signInOrUpViewModel

        initActionBar(binding.toolBar, isShowHomeEnable = true)
        signInOrUpViewModel.initData()
    }
}