package com.zcp.wanAndroid.ui.sign.signUp

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseFragment
import com.zcp.wanAndroid.databinding.FragmentSignUpBinding
import com.zcp.wanAndroid.ui.sign.OnSignClickListener
import com.zcp.wanAndroid.ui.sign.SignInAndUpRepository
import com.zcp.wanAndroid.ui.sign.signUp.di.DaggerSignUpComponent
import com.zcp.wanAndroid.ui.sign.signUp.di.SignUpViewModule
import com.zcp.wanAndroid.ui.sign.signUp.viewModel.SignUpViewModel
import com.zcp.wanAndroid.ui.sign.viewmodel.SignPageData
import javax.inject.Inject

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    var isShowUsedPassword: Boolean = false
    var isRememberUserPassword: Boolean = false
    var signInAndUpRepository: SignInAndUpRepository? = null

    override fun getLayoutResource(): Int = R.layout.fragment_sign_up
    override fun upDataView() {
    }

    override fun viewCreated(mRootView: View) {
        binding.vm = signUpViewModel
        binding.btSignUp.isEnabled = false
        initView()
        setEditText()
    }

    private fun initView() {
        setPassWordStatus(isShowUsedPassword)
        setRememberCheckOutStatus(isRememberUserPassword)
        binding.btSignUp.setOnClickListener {
            signUpViewModel.signUpApp(
                signInAndUpRepository,
                binding.etUserName.text.toString(),
                binding.etUserPassword.text.toString(),
                binding.etUserConfirmPassword.text.toString()
            )
            signUpViewModel.saveUser(
                binding.cbSignUpRemember.isChecked,
                binding.etUserName.text.toString(),
                binding.etUserPassword.text.toString()
            )
        }
        signUpViewModel.signUpData.observe(this, Observer<SignPageData> {
            callback?.onSignUpClickListener(it)
        })
    }

    fun setRememberCheckOutStatus(isRememberPassword: Boolean) {
        isRememberUserPassword = isRememberPassword
        binding.cbSignUpRemember.isChecked = isRememberPassword
    }

    fun setPassWordStatus(it: Boolean) {
        binding.etUserPassword.imeOptions = EditorInfo.IME_ACTION_DONE
        if (it) {
            binding.ivShowPassword.setBackgroundResource(R.drawable.ic_visibility_off_select)
            binding.ivShowConfirmPassword.setBackgroundResource(R.drawable.ic_visibility_off_select)
            binding.etUserPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.etUserConfirmPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
        } else {
            binding.ivShowPassword.setBackgroundResource(R.drawable.ic_visibility_select)
            binding.ivShowConfirmPassword.setBackgroundResource(R.drawable.ic_visibility_select)
            binding.etUserPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.etUserConfirmPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
        }
        binding.etUserPassword.text?.length?.let { length ->
            binding.etUserPassword.setSelection(length)
        }
        binding.etUserConfirmPassword.text?.length?.let { length ->
            binding.etUserConfirmPassword.setSelection(length)
        }
    }

    private fun setEditText() {
        binding.etUserName.apply {
            addTextChangedListener {
                checkSignUpButtonStatus()
            }
        }
        binding.etUserPassword.apply {
            setOnFocusChangeListener { _, hasFocus ->
                binding.inputContainer.isSelected = hasFocus
                binding.ivShowPassword.isSelected = hasFocus
            }
            addTextChangedListener {
                checkSignUpButtonStatus()
            }
        }

        binding.etUserConfirmPassword.apply {
            setOnFocusChangeListener { _, hasFocus ->
                binding.confirmInputContainer.isSelected = hasFocus
                binding.ivShowConfirmPassword.isSelected = hasFocus
            }
            addTextChangedListener {
                checkSignUpButtonStatus()
            }
        }
    }

    private fun checkSignUpButtonStatus() {
        if (!binding.etUserPassword.text.isNullOrEmpty() && !binding.etUserName.text.isNullOrEmpty() && !binding.etUserConfirmPassword.text.isNullOrEmpty()) {
            binding.btSignUp.isEnabled = true
        } else if (binding.etUserPassword.text.isNullOrEmpty() && binding.etUserName.text.isNullOrEmpty() && binding.etUserConfirmPassword.text.isNullOrEmpty()) {
            binding.btSignUp.isEnabled = false
        }
    }

    override fun initInject() {
        DaggerSignUpComponent.builder()
            .applicationComponent(getAppComponent())
            .signUpViewModule(SignUpViewModule(this))
            .build()
            .inject(this)
    }

    var callback: OnSignClickListener? = null

    fun setOnSignInClickListener(callback: OnSignClickListener) {
        this.callback = callback
    }
}