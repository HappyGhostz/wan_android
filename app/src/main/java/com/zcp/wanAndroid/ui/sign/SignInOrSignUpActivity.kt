package com.zcp.wanAndroid.ui.sign

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseActivity
import com.zcp.wanAndroid.databinding.ActivitySignBinding
import com.zcp.wanAndroid.extension.addFragmentWithCallback
import com.zcp.wanAndroid.extension.replaceFragmentWithAnimationsAndCallBack
import com.zcp.wanAndroid.ui.home.HomeActivity
import com.zcp.wanAndroid.ui.sign.di.DaggerSignInOrUpComponent
import com.zcp.wanAndroid.ui.sign.di.SignInOrUpViewModule
import com.zcp.wanAndroid.ui.sign.signIn.SignInFragment
import com.zcp.wanAndroid.ui.sign.signUp.SignUpFragment
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModel
import com.zcp.wanAndroid.ui.sign.viewmodel.SignPageData
import com.zcp.wanAndroid.utils.DialogUtils
import com.zcp.wanAndroid.utils.ResponseLoadStatus
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SignInOrSignUpActivity : BaseActivity<ActivitySignBinding>(),
    OnSignClickListener {

    @Inject
    lateinit var signInOrUpViewModel: SignInOrUpViewModel

    @Inject
    lateinit var signUpFragment: SignUpFragment

    @Inject
    lateinit var signInFragment: SignInFragment

    @Inject
    lateinit var dialogutils: DialogUtils

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is SignInFragment) {
            fragment.setOnSignInClickListener(this)
        } else if (fragment is SignUpFragment) {
            fragment.setOnSignInClickListener(this)
        }
    }

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
        binding.toolBar.setNavigationOnClickListener {
            finish()
        }
        signInOrUpViewModel.initData()
        addFragmentWithCallback(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                super.onFragmentStarted(fm, f)
                setSignInViewClickListener()
            }
        }) {
            add(R.id.sign_in_and_sign_up_container, signInFragment, "signIn")
        }
        initStatusValue()
    }

    private fun initStatusValue() {
        signInOrUpViewModel.initShowPasswordValue()
        signInOrUpViewModel.isShowUsedPassword.value?.let {
            setPassWordStatus(it)
        }
        signInOrUpViewModel.isShowUsedPassword.observe(this, Observer<Boolean> {
            when {
                signInFragment.isVisible -> {
                    signInFragment.setPassWordStatus(it)
                }
                signUpFragment.isVisible -> {
                    signUpFragment.setPassWordStatus(it)
                }
                else -> {
                    setPassWordStatus(it)
                }
            }
        })

        signInOrUpViewModel.isRememberUserPassword.value?.let {
            setRememberPassWordStatus(it)
        }

        signInOrUpViewModel.isRememberUserPassword.observe(this, Observer<Boolean> {
            when {
                signInFragment.isVisible -> {
                    signInFragment.setRememberCheckOutStatus(it)
                }
                signUpFragment.isVisible -> {
                    signUpFragment.setRememberCheckOutStatus(it)
                }
                else -> {
                    setPassWordStatus(it)
                }
            }
        })
    }

    private fun setSignInViewClickListener() {
        signInFragment.binding.btGoSignUp.setOnClickListener {
            flipCard()
        }
        signInFragment.binding.ivShowPassword.setOnClickListener {
            signInOrUpViewModel.isShowUsedPassword.value?.let { isShowUsedPassword ->
                signInOrUpViewModel.postIsShowUsedPasswordValue(!isShowUsedPassword)
                signInOrUpViewModel.saveIsShowUsedPasswordValue(!isShowUsedPassword)
            }
        }
        signInFragment.binding.cbRememberPassword.setOnClickListener {
            signInOrUpViewModel.isRememberUserPassword.value?.let { isRememberPassword ->
                signInOrUpViewModel.postIsRememberPasswordValue(!isRememberPassword)
                signInOrUpViewModel.saveIsRememberPasswordValue(!isRememberPassword)
            }
        }
    }

    private fun setPassWordStatus(it: Boolean) {
        signInFragment.isShowUsedPassword = it
        signUpFragment.isShowUsedPassword = it
    }

    private fun setRememberPassWordStatus(it: Boolean) {
        signInFragment.isRememberUserPassword = it
        signUpFragment.isRememberUserPassword = it
    }

    private fun flipCard() {
        if (signInOrUpViewModel.showingSignUpFragment) {
            supportFragmentManager.popBackStack()
            signInOrUpViewModel.showingSignUpFragment = false
            return
        }
        signInOrUpViewModel.showingSignUpFragment = true
        replaceFragmentWithAnimationsAndCallBack(
            R.animator.card_flip_left_in,
            R.animator.card_flip_left_out,
            R.animator.card_flip_right_in,
            R.animator.card_flip_right_out,
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                    super.onFragmentStarted(fm, f)
                    signUpFragment.binding.btGoSignIn.setOnClickListener {
                        flipCard()
                    }
                    signUpFragment.binding.ivShowPassword.setOnClickListener {
                        signInOrUpViewModel.isShowUsedPassword.value?.let { isShowUsedPassword ->
                            signInOrUpViewModel.postIsShowUsedPasswordValue(!isShowUsedPassword)
                            signInOrUpViewModel.saveIsShowUsedPasswordValue(!isShowUsedPassword)
                        }
                    }
                    signUpFragment.binding.ivShowConfirmPassword.setOnClickListener {
                        signInOrUpViewModel.isShowUsedPassword.value?.let { isShowUsedPassword ->
                            signInOrUpViewModel.postIsShowUsedPasswordValue(!isShowUsedPassword)
                            signInOrUpViewModel.saveIsShowUsedPasswordValue(!isShowUsedPassword)
                        }
                    }
                    signUpFragment.binding.cbSignUpRemember.setOnClickListener {
                        signInOrUpViewModel.isRememberUserPassword.value?.let { isRememberPassword ->
                            signInOrUpViewModel.postIsRememberPasswordValue(!isRememberPassword)
                            signInOrUpViewModel.saveIsRememberPasswordValue(!isRememberPassword)
                        }
                    }
                }
            }) {
            hide(signInFragment)
            add(R.id.sign_in_and_sign_up_container, signUpFragment.apply {
                isRememberUserPassword =
                    signInOrUpViewModel.isRememberUserPassword.value ?: false
                isShowUsedPassword = signInOrUpViewModel.isShowUsedPassword.value ?: false
            }, "signUp")
            addToBackStack(null)
        }
    }

    override fun onSignInClickListener(signPageData: SignPageData) {
        afterClickSignInOrUp(signPageData)
    }

    override fun onSignUpClickListener(signPageData: SignPageData) {
        afterClickSignInOrUp(signPageData)
    }

    private fun afterClickSignInOrUp(signPageData: SignPageData) {
        when (signPageData.loadingStatus) {
            ResponseLoadStatus.LOADING -> {
                dialogutils.createdLoadingDialog(
                    supportFragmentManager,
                    getWidthScreen() / 4
                )
            }
            ResponseLoadStatus.ERROR -> {
                dialogutils.dismissDialog()
                dialogutils.createdNetErrorDialog(this, supportFragmentManager)
            }
            ResponseLoadStatus.SUCCEEDED -> {
                dialogutils.dismissDialog()
                if (signPageData.signData?.errorMsg.isNullOrEmpty() && signPageData.signData?.errorCode == 0) {
                    startActivity<HomeActivity>()
                    finish()
                } else {
                    dialogutils.createdInfoDialog(
                        this,
                        supportFragmentManager,
                        info = signPageData.signData?.errorMsg
                    )
                }
            }
            ResponseLoadStatus.UNKNOWN -> {
                dialogutils.dismissDialog()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialogutils.dismissDialog()
    }
}