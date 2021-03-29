package com.zcp.wanAndroid.ui.sign

import com.zcp.wanAndroid.module.SignInData
import com.zcp.wanAndroid.ui.sign.apiService.SignInAndUpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SignInAndUpRepository(private val signApi: SignInAndUpService) {
    suspend fun signIn(userName: String, password: String): Flow<SignInData> {
        return flow<SignInData> {
            val signInData = signApi.signIn(userName, password)
            emit(signInData)
        }.flowOn(Dispatchers.IO)
    }
}