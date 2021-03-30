package com.zcp.wanAndroid.ui.main.apiService

import com.zcp.wanAndroid.module.Article
import com.zcp.wanAndroid.module.Banner
import com.zcp.wanAndroid.module.BaseResponseData
import com.zcp.wanAndroid.module.TopArticle
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApiService {
    @GET("/banner/json")
    suspend fun getBanner(): Banner

    @GET("/article/top/json")
    suspend fun getTopArticle(): TopArticle

    @GET("/article/list/{index}/json")
    suspend fun getFirstArticle(@Path("index") index: Int): Article

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): BaseResponseData

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): BaseResponseData
}