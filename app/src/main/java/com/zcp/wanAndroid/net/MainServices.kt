package com.zcp.wanAndroid.net

import com.zcp.wanAndroid.module.Article
import retrofit2.http.GET
import retrofit2.http.Path

interface MainServices {
    @GET("/article/list/{index}/json")
    suspend fun getArticle(@Path("index") index: Int): Article
}