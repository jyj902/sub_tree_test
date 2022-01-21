package com.hnh.data.service

import com.hnh.data.VERSION
import com.hnh.data.entity.VersionRes
import retrofit2.http.GET

/**
 * Created by hyerim on 2021/10/08...
 */
interface VersionService {
    @GET("$VERSION")
    suspend fun getVersion(): VersionRes
}