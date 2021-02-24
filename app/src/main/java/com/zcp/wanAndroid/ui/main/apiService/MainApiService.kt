package com.zcp.wanAndroid.ui.main.apiService

import com.zcp.wanAndroid.module.Article
import com.zcp.wanAndroid.module.Banner
import com.zcp.wanAndroid.module.TopArticle
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApiService {
    @GET("/banner/json")
    suspend fun getBanner(): Banner

    @GET("/article/top/json")
    suspend fun getTopArticle(): TopArticle

    @GET("/article/list/{index}/json")
    suspend fun getFirstArticle(@Path("index") index: Int): Article
}