package com.zcp.wanAndroid.ui.sign.apiService

import com.zcp.wanAndroid.module.SignData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignInAndUpService {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun signIn(
        @Field("username") userName: String,
        @Field("password") password: String
    ): SignData

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun signUp(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): SignData
}