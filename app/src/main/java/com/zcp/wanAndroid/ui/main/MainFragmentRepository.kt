package com.zcp.wanAndroid.ui.main

import com.zcp.wanAndroid.module.BaseResponseData
import com.zcp.wanAndroid.ui.main.apiService.MainApiService
import com.zcp.wanAndroid.ui.main.viewmodel.MainListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainFragmentRepository(private val mainAPi: MainApiService) {

    suspend fun getData(pageIndex: Int): Flow<MainListData> {
        return  flow<MainListData> {
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
    }

    suspend fun refreshData(pageIndex: Int): Flow<MainListData> {
        return  flow<MainListData> {
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
    }

    suspend fun loadMoreData(pageIndex: Int): Flow<MainListData> {
        return  flow<MainListData> {
            val article = mainAPi.getFirstArticle(pageIndex)
            var articles = article.data?.datas?.toMutableList()
            var mainListData = MainListData(articleInfos = articles)
            emit(mainListData)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun collectArticle(id: Int): Flow<BaseResponseData>{
        return  flow<BaseResponseData> {
            val response = mainAPi.collectArticle(id)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun unCollectArticle(id: Int): Flow<BaseResponseData>{
        return  flow<BaseResponseData> {
            val response = mainAPi.unCollectArticle(id)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}