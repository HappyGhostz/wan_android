package com.zcp.wanAndroid.ui.sign

import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseActivity
import com.zcp.wanAndroid.customView.CustomDialogFragment
import com.zcp.wanAndroid.databinding.ActivitySignBinding
import com.zcp.wanAndroid.extension.addFragmentWithCallback
import com.zcp.wanAndroid.extension.replaceFragmentWithAnimationsAndCallBack
import com.zcp.wanAndroid.ui.home.HomeActivity
import com.zcp.wanAndroid.ui.sign.di.DaggerSignInOrUpComponent
import com.zcp.wanAndroid.ui.sign.di.SignInOrUpViewModule
import com.zcp.wanAndroid.ui.sign.signIn.SignInFragment
import com.zcp.wanAndroid.ui.sign.signIn.viewModel.SignInPageData
import com.zcp.wanAndroid.ui.sign.signUp.SignUpFragment
import com.zcp.wanAndroid.ui.sign.viewmodel.SignInOrUpViewModel
import com.zcp.wanAndroid.utils.DialogUtils
import com.zcp.wanAndroid.utils.ResponseLoadStatus
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SignInOrSignUpActivity : BaseActivity<ActivitySignBinding>(),
    SignInFragment.OnSignInClickListener {

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
            if (signInFragment.isVisible) {
                signInFragment.setPassWordStatus(it)
            } else {
                setPassWordStatus(it)
            }
        })

        signInOrUpViewModel.isRememberUserPassword.value?.let {
            setRememberPassWordStatus(it)
        }

        signInOrUpViewModel.isRememberUserPassword.observe(this, Observer<Boolean> {
            if (signInFragment.isVisible) {
                signInFragment.setRememberCheckOutStatus(it)
            } else {
                setRememberPassWordStatus(it)
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
    }

    private fun setRememberPassWordStatus(it: Boolean) {
        signInFragment.isRememberUserPassword = it
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
                }
            }) {
            hide(signInFragment)
            add(R.id.sign_in_and_sign_up_container, signUpFragment, "signUp")
            addToBackStack(null)
        }
    }

    override fun onSignInClickListener(signInPageData: SignInPageData) {
        when (signInPageData.loadingStatus) {
            ResponseLoadStatus.LOADING -> {
                dialogutils.dismissDialog()
                dialogutils.createdLoadingDialog(
                    supportFragmentManager,
                    getWidthScreen() / 4
                )
            }
            ResponseLoadStatus.ERROR -> {
                dialogutils.dismissDialog()
                dialogutils.createdNetErrorDialog(this, supportFragmentManager)
            }
            ResponseLoadStatus.SUCCESSED -> {
                dialogutils.dismissDialog()
                if (signInPageData.signInData?.errorMsg.isNullOrEmpty() && signInPageData.signInData?.errorCode == 0) {
                    startActivity<HomeActivity>()
                } else {
                    dialogutils.createdInfoDialog(
                        this,
                        supportFragmentManager,
                        info = signInPageData.signInData?.errorMsg
                    )
                }
            }
        }
    }
}