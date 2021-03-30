package com.zcp.wanAndroid.ui.main.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zcp.wanAndroid.module.ArticleInfo
import com.zcp.wanAndroid.module.Banner
import com.zcp.wanAndroid.module.BaseResponseData
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.MainFragmentRepository
import com.zcp.wanAndroid.utils.ResponseLoadStatus
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Parcelize
data class MainListData(
    var articleInfos: List<ArticleInfo>? = null,
    var banner: Banner? = null
) : Parcelable {
}

@Parcelize
data class CollectStatusData(
    var loadStatus: ResponseLoadStatus = ResponseLoadStatus.UNKNOWN,
    var position: Int = 0,
    var msg: String = ""
) : Parcelable {
}

class MainViewModel(
    private val repository: MainFragmentRepository,
    private val layoutStatusViewModel: LayoutStatusViewModel
) : ViewModel() {
    private val _mainListData = MutableLiveData<MainListData>()
    private val _mainListMoreData = MutableLiveData<MainListData>()
    val mainListData: LiveData<MainListData> = _mainListData
    val mainListMoreData: LiveData<MainListData> = _mainListMoreData

    private val _collectStatusData = MutableLiveData<CollectStatusData>()
    val collectStatusData: LiveData<CollectStatusData> = _collectStatusData

    var pageIndex = 0
    fun initData() {
        pageIndex = 0
        viewModelScope.launch {
            repository.getData(pageIndex)
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
            repository.refreshData(pageIndex)
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
            repository.loadMoreData(pageIndex)
                .catch {
                    _mainListMoreData.postValue(MainListData())
                }
                .collectLatest {
                    _mainListMoreData.postValue(it)
                    pageIndex++
                }
        }
    }

    fun setCollectArticleInfo(articleInfo: ArticleInfo, position: Int) {
        viewModelScope.launch {
            collectOrUnCollectArticleInfo(articleInfo.id, articleInfo.collect)
                .onStart {
                    _collectStatusData.postValue(
                        CollectStatusData(
                            ResponseLoadStatus.LOADING,
                            position
                        )
                    )
                }
                .catch {
                    _collectStatusData.postValue(
                        CollectStatusData(
                            ResponseLoadStatus.ERROR,
                            position,
                            "网络出现问题..."
                        )
                    )
                }.collectLatest {
                    if (it.errorCode != 0 && !it.errorMsg.isNullOrEmpty()) {
                        _collectStatusData.postValue(
                            CollectStatusData(
                                ResponseLoadStatus.ERROR,
                                position,
                                it.errorMsg
                            )
                        )
                    }
                    _collectStatusData.postValue(
                        CollectStatusData(
                            ResponseLoadStatus.SUCCEEDED,
                            position
                        )
                    )
                }
        }
    }

    private suspend fun collectOrUnCollectArticleInfo(id: Int, isCollect: Boolean): Flow<BaseResponseData> {
        return if (isCollect) {
            repository.unCollectArticle(id)
        } else {
            repository.collectArticle(id)
        }
    }
}