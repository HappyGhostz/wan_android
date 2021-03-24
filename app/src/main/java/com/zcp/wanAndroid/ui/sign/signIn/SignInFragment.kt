package com.zcp.wanAndroid.ui.sign.signIn

import android.view.View
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.base.BaseFragment
import com.zcp.wanAndroid.databinding.FragmentSignInBinding
import com.zcp.wanAndroid.ui.sign.signIn.viewModel.SignInViewModel
import javax.inject.Inject

class SignInFragment: BaseFragment<FragmentSignInBinding>() {

    @Inject
    lateinit var signInViewModel: SignInViewModel

    override fun getLayoutResource(): Int  = R.layout.fragment_sign_in
    override fun upDataView() {
    }

    override fun viewCreated(mRootView: View) {

    }

    override fun initInject() {
    }
}