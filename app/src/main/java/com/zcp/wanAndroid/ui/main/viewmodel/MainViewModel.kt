package com.zcp.wanAndroid.ui.main.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zcp.wanAndroid.module.ArticleInfo
import com.zcp.wanAndroid.module.Banner
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.apiService.MainApiService
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Parcelize
data class MainListData(
    var articleInfos: List<ArticleInfo>? = null,
    var banner: Banner? = null
) : Parcelable {
}

class MainViewModel(
    private val mainAPi: MainApiService,
    private val layoutStatusViewModel: LayoutStatusViewModel
) : ViewModel() {
    private val _mainListData = MutableLiveData<MainListData>()
    private val _mainListMoreData = MutableLiveData<MainListData>()
    val mainListData: LiveData<MainListData> = _mainListData
    val mainListMoreData: LiveData<MainListData> = _mainListMoreData
    var pageIndex = 0
    fun initData() {
        pageIndex = 0
        viewModelScope.launch {
            flow<MainListData> {
                val banner = mainAPi.getBanner()
                val topArticle = mainAPi.getTopArticle()
                val article = mainAPi.getFirstArticle(pageIndex)
                var articles = article.data?.datas?.toMutableList()
                var topArticles = topArticle.data?.toMutableList()
                if (articles != null) {
                    topArticles?.addAll(articles)
                }
                var mainListData = MainListData(articleInfos = topArticles, banner = banner)
                emit(mainListData)
            }.flowOn(Dispatchers.IO)
                .onStart {
                    layoutStatusViewModel.showLoadingPage()
                }
                .catch {
                    layoutStatusViewModel.showErrorPage()
                }
                .collectLatest {
                    layoutStatusViewModel.hideAllPage()
                    if (it.articleInfos.isNullOrEmpty()) {
                        layoutStatusViewModel.showEmptyPage()
                    } else {
                        _mainListData.postValue(it)
                        pageIndex++
                    }
                }
        }
    }

    fun refreshData() {
        pageIndex = 0
        viewModelScope.launch {
            flow<MainListData> {
                val banner = mainAPi.getBanner()
                val topArticle = mainAPi.getTopArticle()
                val article = mainAPi.getFirstArticle(pageIndex)
                var articles = article.data?.datas?.toMutableList()
                var topArticles = topArticle.data?.toMutableList()
                if (articles != null) {
                    topArticles?.addAll(articles)
                }
                var mainListData = MainListData(articleInfos = topArticles, banner = banner)
                emit(mainListData)
            }.flowOn(Dispatchers.IO)
                .catch {
                    layoutStatusViewModel.showErrorPage()
                }
                .collectLatest {
                    layoutStatusViewModel.hideAllPage()
                    if (it.articleInfos.isNullOrEmpty()) {
                        layoutStatusViewModel.showEmptyPage()
                    } else {
                        _mainListData.postValue(it)
                        pageIndex++
                    }
                }
        }
    }

    fun loadMoreData() {
        viewModelScope.launch {
            flow<MainListData> {
                val article = mainAPi.getFirstArticle(pageIndex)
                var articles = article.data?.datas?.toMutableList()
                var mainListData = MainListData(articleInfos = articles)
                emit(mainListData)
            }.flowOn(Dispatchers.IO)
                .catch {
                    _mainListMoreData.postValue(MainListData())
                }
                .collectLatest {
                    _mainListMoreData.postValue(it)
                    pageIndex++
                }
        }
    }
}