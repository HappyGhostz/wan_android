package com.zcp.wanAndroid.ui.sign.signIn

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseFragment
import com.zcp.wanAndroid.databinding.FragmentSignInBinding
import com.zcp.wanAndroid.ui.sign.OnSignClickListener
import com.zcp.wanAndroid.ui.sign.SignInAndUpRepository
import com.zcp.wanAndroid.ui.sign.signIn.di.DaggerSignInComponent
import com.zcp.wanAndroid.ui.sign.signIn.di.SignInViewModule
import com.zcp.wanAndroid.ui.sign.signIn.viewModel.SignInViewModel
import com.zcp.wanAndroid.ui.sign.viewmodel.SignPageData
import javax.inject.Inject

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    @Inject
    lateinit var signInViewModel: SignInViewModel

    var isShowUsedPassword: Boolean = false
    var isRememberUserPassword: Boolean = false
    var signInAndUpRepository: SignInAndUpRepository? = null

    override fun getLayoutResource(): Int = R.layout.fragment_sign_in
    override fun upDataView() {

    }

    override fun viewCreated(mRootView: View) {
        binding.btSignIn.isEnabled = false
        binding.vm = signInViewModel
        initView()
        signInViewModel.initData()
        setEditText()
    }

    private fun initView() {
        setPassWordStatus(isShowUsedPassword)
        setRememberCheckOutStatus(isRememberUserPassword)
        binding.btSignIn.setOnClickListener {
            signInViewModel.signInApp(
                signInAndUpRepository,
                binding.etUserName.text.toString(),
                binding.etUserPassword.text.toString()
            )
            signInViewModel.saveOrCleanUser(
                binding.cbRememberPassword.isChecked,
                binding.etUserName.text.toString(),
                binding.etUserPassword.text.toString()
            )
        }
        signInViewModel.signData.observe(this, Observer<SignPageData> {
            callback?.onSignInClickListener(it)
        })
        signInViewModel.userName.observe(this, Observer<String> {
            binding.etUserName.setText(it)
        })

        signInViewModel.password.observe(this, Observer<String> {
            binding.etUserPassword.setText(it)
        })

    }

    fun setRememberCheckOutStatus(isRememberPassword: Boolean) {
        isRememberUserPassword = isRememberPassword
        binding.cbRememberPassword.isChecked = isRememberPassword
    }

    fun setPassWordStatus(it: Boolean) {
        binding.etUserPassword.imeOptions = EditorInfo.IME_ACTION_DONE
        if (it) {
            binding.ivShowPassword.setBackgroundResource(R.drawable.ic_visibility_off_select)
            binding.etUserPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
        } else {
            binding.ivShowPassword.setBackgroundResource(R.drawable.ic_visibility_select)
            binding.etUserPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
        }
        binding.etUserPassword.text?.length?.let { length ->
            binding.etUserPassword.setSelection(length)
        }
    }

    private fun setEditText() {
        binding.etUserName.apply {
            addTextChangedListener {
                checkSignInButtonStatus()
            }
        }
        binding.etUserPassword.apply {
            setOnFocusChangeListener { _, hasFocus ->
                binding.inputContainer.isSelected = hasFocus
                binding.ivShowPassword.isSelected = hasFocus
            }
            addTextChangedListener {
                checkSignInButtonStatus()
            }
        }
    }

    private fun checkSignInButtonStatus() {
        if (!binding.etUserPassword.text.isNullOrEmpty() && !binding.etUserName.text.isNullOrEmpty()) {
            binding.btSignIn.isEnabled = true
        } else if (binding.etUserPassword.text.isNullOrEmpty() && binding.etUserName.text.isNullOrEmpty()) {
            binding.btSignIn.isEnabled = false
        }
    }

    override fun initInject() {
        DaggerSignInComponent.builder()
            .applicationComponent(getAppComponent())
            .signInViewModule(SignInViewModule(this))
            .build()
            .inject(this)
    }

    var callback: OnSignClickListener? = null

    fun setOnSignInClickListener(callback: OnSignClickListener) {
        this.callback = callback
    }

}