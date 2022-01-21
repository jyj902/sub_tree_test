package com.hnh.data.source

import com.hnh.data.entity.UserRes
import com.hnh.data.service.AuthService
import javax.inject.Inject

/**
 * Created by hyerim on 2021/10/25...
 */
interface UserRemoteSource {
    suspend fun getUser(token: String, type: String): UserRes
    suspend fun getUserToken(token: String): UserRes
}

class UserRemoteSourceImpl @Inject constructor(
    private val authService: AuthService
) : UserRemoteSource {
    override suspend fun getUser(token: String, type: String): UserRes {
        return authService.getUser(token, type)
    }

    override suspend fun getUserToken(token: String): UserRes {
        return authService.getUserToken(token)
    }
}