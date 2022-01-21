package com.hnh.data.source.local

import com.hnh.data.entity.UserRes
import com.hnh.data.service.AuthService
import com.hnh.data.source.UserRemoteSource
import javax.inject.Inject

/**
 * Created by hyerim on 2021/11/02...
 */
interface UserLocalSource {
    suspend fun getUser(token: String, type: String): UserRes
}

class UserLocalSourceImpl @Inject constructor(
    private val dataStore: AuthService
) : UserLocalSource {
    override suspend fun getUser(token: String, type: String): UserRes {
        return dataStore.getUser(token, type)
    }
}