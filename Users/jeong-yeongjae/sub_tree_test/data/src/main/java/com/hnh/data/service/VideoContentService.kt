package com.hnh.data.service

import com.hnh.data.VIDEO
import com.hnh.data.VIDEO_LIST
import com.hnh.data.VIDEO_LIST_COURSE
import com.hnh.data.entity.VideoContentsRes
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by hyerim on 2021/09/26...
 */
interface VideoContentService {
    @GET("$VIDEO_LIST_COURSE")
    suspend fun getVideoContentsList(
        @Path("id") id: Int
    ): List<VideoContentsRes>

    @GET("$VIDEO_LIST")
    suspend fun getVideoContentsListAll(): List<VideoContentsRes>

    @GET("$VIDEO")
    suspend fun getVideoContent(
        @Path("id") id: String
    ): VideoContentsRes
}