package com.hnh.domain.repository

import com.hnh.domain.entity.User

/**
 * Created by hyerim on 2021/10/25...
 */
interface UserRepository {
    suspend fun getUser(token: String, type: String): User

    suspend fun getUserToken(token: String): User
}