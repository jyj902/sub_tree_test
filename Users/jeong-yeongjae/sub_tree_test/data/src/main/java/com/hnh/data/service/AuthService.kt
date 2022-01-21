package com.hnh.data.service

import com.hnh.data.USER_INFO
import com.hnh.data.USER_LOGIN
import com.hnh.data.entity.UserRes
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by hyerim on 2021/10/25...
 */
interface AuthService {
    @GET("$USER_LOGIN")
    suspend fun getUser(
        @Path("token") token: String,
        @Path("type") type: String
    ): UserRes

    @GET("$USER_INFO")
    suspend fun getUserToken(@Header("token") token: String): UserRes
}