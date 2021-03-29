package com.zcp.wanAndroid.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentManager
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.customView.CustomDialogFragment
import org.jetbrains.anko.textColor

class DialogUtils(private val res: ResourcesProvider) {

    private var customDialogFragment: CustomDialogFragment? = null

    fun createdLoadingDialog(manager: FragmentManager, customDialogWidth: Int) {
        customDialogFragment = CustomDialogFragment.Builder().apply {
            hasTitle = false
            isCancelable = false
            isTransparent = true
            mResId = R.layout.layout_dialog_loading
            dialogWidth = customDialogWidth
            dimAmount = 0.5F
            gravity = Gravity.CENTER
        }.creat()
        customDialogFragment?.show(manager, "loading")
    }

    fun createdNetErrorDialog(
        context: Context?,
        manager: FragmentManager,
        customDialogWidth: Int = 0,
    ) {
        val errorView =
            LayoutInflater.from(context).inflate(R.layout.layout_dialog_net_error, null)
        errorView.findViewById<AppCompatButton>(R.id.bt_cancel).setOnClickListener {
            dismissDialog()
        }
        errorView.findViewById<AppCompatButton>(R.id.bt_confirm).setOnClickListener {
            dismissDialog()
        }
        customDialogFragment = CustomDialogFragment.Builder().apply {
            hasTitle = false
            isCancelable = false
            isTransparent = true
            mView = errorView
            dialogWidth = customDialogWidth
            dimAmount = 0.5F
        }.show(manager, "net_error")
    }

    fun createdInfoDialog(
        context: Context?,
        manager: FragmentManager,
        customDialogWidth: Int = 0,
        info: String? = ""
    ) {
        val errorView =
            LayoutInflater.from(context).inflate(R.layout.layout_dialog_net_error, null)
        errorView.findViewById<AppCompatButton>(R.id.bt_cancel).setOnClickListener {
            dismissDialog()
        }
        errorView.findViewById<AppCompatButton>(R.id.bt_confirm).setOnClickListener {
            dismissDialog()
        }

        errorView.findViewById<AppCompatTextView>(R.id.tv_title).apply {
            text = res.resources.getText(R.string.info)
            textColor = res.resources.getColor(R.color.colorPrimary)
        }

        errorView.findViewById<AppCompatTextView>(R.id.tv_info).apply {
            text = info
        }

        customDialogFragment = CustomDialogFragment.Builder().apply {
            hasTitle = false
            isCancelable = false
            isTransparent = true
            mView = errorView
            dialogWidth = customDialogWidth
            dimAmount = 0.5F
        }.show(manager, "net_error")
    }

    fun isShowingDialog(): Boolean? = customDialogFragment?.isVisible

    fun dismissDialog() {
        customDialogFragment?.dismiss()
    }
}