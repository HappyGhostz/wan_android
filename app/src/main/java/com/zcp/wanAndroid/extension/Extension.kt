package com.zcp.wanAndroid.extension

import android.content.Context
import android.widget.ImageView
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.zcp.wanAndroid.utils.ImageLoadUtils

inline fun FragmentManager.inTransactionWithCallBack(
    callBack: FragmentManager.FragmentLifecycleCallbacks,
    func: FragmentTransaction.() -> FragmentTransaction
) {
    registerFragmentLifecycleCallbacks(callBack, true)
    beginTransaction().func().commit()
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun FragmentManager.inTransactionWithAnimations(
    enter: Int,
    exit: Int,
    popEnter: Int,
    popExit: Int,
    func: FragmentTransaction.() -> FragmentTransaction
) {
    beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit).func().commit()
}

inline fun FragmentManager.inTransactionWithAnimationsAndCallBack(
    enter: Int,
    exit: Int,
    popEnter: Int,
    popExit: Int,
    callBack: FragmentManager.FragmentLifecycleCallbacks,
    func: FragmentTransaction.() -> FragmentTransaction
) {
    registerFragmentLifecycleCallbacks(callBack, true)
    beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit).func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, fragmentTag: String? = null) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment, fragmentTag)
    }
}

fun AppCompatActivity.addFragment(func: FragmentTransaction.() -> FragmentTransaction) {
    supportFragmentManager.inTransaction(func)
}

fun AppCompatActivity.addFragmentWithCallback(
    callBack: FragmentManager.FragmentLifecycleCallbacks,
    func: FragmentTransaction.() -> FragmentTransaction
) {
    supportFragmentManager.inTransactionWithCallBack(callBack, func)
}

fun AppCompatActivity.addFragmentWithAnimations(
    fragment: Fragment,
    frameId: Int,
    fragmentTag: String? = null,
    enter: Int,
    exit: Int,
    popEnter: Int,
    popExit: Int,
) {
    supportFragmentManager.inTransactionWithAnimations(enter, exit, popEnter, popExit) {
        add(frameId, fragment, fragmentTag)
    }
}

fun Fragment.addChildFragmentWithAnimations(
    fragment: Fragment,
    frameId: Int,
    fragmentTag: String? = null,
    enter: Int,
    exit: Int,
    popEnter: Int,
    popExit: Int,
) {
    childFragmentManager.inTransactionWithAnimations(enter, exit, popEnter, popExit) {
        add(frameId, fragment, fragmentTag)
    }
}

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    frameId: Int,
    fragmentTag: String? = null
) {
    supportFragmentManager.inTransaction {
        replace(frameId, fragment, fragmentTag)
    }
}

fun AppCompatActivity.replaceFragmentWithAnimations(
    fragment: Fragment,
    frameId: Int,
    fragmentTag: String? = null,
    enter: Int,
    exit: Int,
    popEnter: Int,
    popExit: Int,
) {
    supportFragmentManager.inTransactionWithAnimations(enter,exit,popEnter,popExit) {
        replace(frameId, fragment, fragmentTag)
        addToBackStack(null)
    }
}

fun AppCompatActivity.replaceFragmentWithAnimationsAndCallBack(
    enter: Int,
    exit: Int,
    popEnter: Int,
    popExit: Int,
    callBack: FragmentManager.FragmentLifecycleCallbacks,
    func: FragmentTransaction.() -> FragmentTransaction
) {
    supportFragmentManager.inTransactionWithAnimationsAndCallBack(enter,exit,popEnter,popExit,callBack, func)
}

fun AppCompatActivity.hideFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction {
        hide(fragment)
    }
}

fun AppCompatActivity.showFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction {
        show(fragment)
    }
}


fun ImageView.loadImage(
    src: String?,
    placeholder: Int? = null,
    error: Int? = null,
    roundedCorner: Int? = null,
    isFitCenter: Boolean = false
) {
    val imageLoadUtils = ImageLoadUtils()
    imageLoadUtils.loadImage(src, placeholder, error, roundedCorner, isFitCenter, this)
}