package com.zcp.wanAndroid.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, fragmentTag: String? = null) {
    supportFragmentManager.inTransaction {
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