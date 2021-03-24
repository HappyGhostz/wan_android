package com.zcp.wanAndroid.ui.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleObserver
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseActivity
import com.zcp.wanAndroid.databinding.ActivitySignBinding
import com.zcp.wanAndroid.extension.addFragment
import com.zcp.wanAndroid.extension.addFragmentWithCallback
import com.zcp.wanAndroid.extension.replaceFragmentWithAnimations
import com.zcp.wanAndroid.extension.replaceFragmentWithAnimationsAndCallBack
import com.zcp.wanAndroid.ui.sign.di.DaggerSignInOrUpComponent
import com.zcp.wanAndroid.ui.sign.di.SignInOrUpViewModule
import com.zcp.wanAndroid.ui.sign.signIn.SignInFragment
import com.zcp.wanAndroid.ui.sign.signUp.SignUpFragment
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModel
import javax.inject.Inject

class SignInOrSignUpActivity : BaseActivity<ActivitySignBinding>() {

    @Inject
    lateinit var signInOrUpViewModel: SignInOrUpViewModel

    @Inject
    lateinit var signUpFragment: SignUpFragment

    @Inject
    lateinit var signInFragment: SignInFragment


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
        addFragmentWithCallback(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                super.onFragmentStarted(fm, f)
                signInFragment.binding.btGoSignUp.setOnClickListener {
                    flipCard()
                }
            }
        }) {
            add(R.id.sign_in_and_sign_up_container, signInFragment, "signIn")
        }
    }

    private fun flipCard() {
        if (signInOrUpViewModel.showingSignUpFragment) {
            supportFragmentManager.popBackStack()
            signInOrUpViewModel.showingSignUpFragment = false
            return
        }
        signInOrUpViewModel.showingSignUpFragment = true

        replaceFragmentWithAnimationsAndCallBack(
            R.animator.card_flip_right_in,
            R.animator.card_flip_right_out,
            R.animator.card_flip_left_in,
            R.animator.card_flip_left_out,
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                    super.onFragmentStarted(fm, f)
                    signUpFragment.binding.btGoSignIn.setOnClickListener {
                        flipCard()
                    }
                }
            }) {
            replace(R.id.sign_in_and_sign_up_container, signUpFragment, "signUp")
            addToBackStack(null)
        }
    }
}