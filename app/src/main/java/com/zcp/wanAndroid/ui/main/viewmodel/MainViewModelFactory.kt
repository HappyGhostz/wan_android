package com.zcp.wanAndroid.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.MainFragmentRepository
import com.zcp.wanAndroid.ui.main.apiService.MainApiService
import retrofit2.Retrofit

class MainViewModelFactory(
    private val repository: MainFragmentRepository,
    private val layoutStatusViewModel: LayoutStatusViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository, layoutStatusViewModel) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    }
}