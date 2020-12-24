package com.zcp.wanAndroid.ui.home.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    var toolbarName = ObservableField<String>("首页")

    fun upDateToolbarName(name: String) {
        toolbarName.set(name);
    }
}