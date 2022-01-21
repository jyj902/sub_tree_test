package com.hnh.data.repositoryimpl

import com.hnh.data.source.UserRemoteSource
import com.hnh.data.source.local.UserLocalSource
import com.hnh.domain.entity.User
import com.hnh.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by hyerim on 2021/10/25...
 */
class UserRepositoryImpl @Inject constructor(
    private val userLocalSource: UserLocalSource,
    private val userRemoteSource: UserRemoteSource
) : UserRepository {
    override suspend fun getUser(token: String, type: String): User {
        return userRemoteSource.getUser(token = token, type = type)
    }

    override suspend fun getUserToken(token: String): User {
        return userRemoteSource.getUserToken(token)
    }
}