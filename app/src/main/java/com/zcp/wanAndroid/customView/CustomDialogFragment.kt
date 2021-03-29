package com.zcp.wanAndroid.customView

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


class CustomDialogFragment private constructor(builder: Builder) : DialogFragment() {

    var builder: Builder = builder
    var mView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!builder.hasTitle) {
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        if (builder.mView != null) {
            mView = builder.mView
        } else if (builder.mResId != 0) {
            mView = inflater.inflate(builder.mResId, container, false)
        }
        return mView
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(builder.isCancelable)
        setWindow()
    }

    private fun setWindow() {
        dialog?.window?.let {window->
             //设置窗体背景色透明
            if (builder.isTransparent) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            } else {
                window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            }
            //设置宽高
            val layoutParams = window.attributes
            if (builder.dialogWidth > 0) {
                layoutParams.width = builder.dialogWidth
            } else {
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            }
            if (builder.dialogHeight > 0) {
                layoutParams.height = builder.dialogHeight
            } else {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            //透明度
            layoutParams.dimAmount = builder.dimAmount
            //位置
            layoutParams.gravity = builder.gravity
            if (builder.mTheme > 0) {
                layoutParams.windowAnimations = builder.mTheme
            }
            window.attributes = layoutParams
        }
    }


    class Builder {
        var mView: View? = null
        var mResId = 0
        var hasTitle = false
        var mTheme = 0
        var dialogHeight = 0
        var dialogWidth = 0
        var dimAmount = 0f
        var gravity = 0
        var isTransparent = false
        var isCancelable = false

        fun creat(): CustomDialogFragment {
            return CustomDialogFragment(this)
        }

        fun show(manager: FragmentManager, tag: String?): CustomDialogFragment {
            val customDialog: CustomDialogFragment = creat()
            customDialog.show(manager, tag)
            return customDialog
        }
    }
}