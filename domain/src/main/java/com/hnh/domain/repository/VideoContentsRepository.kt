package com.hnh.domain.repository

import com.hnh.domain.entity.VideoContents

/**
 * Created by hyerim on 2021/09/26...
 */
interface VideoContentsRepository {
    suspend fun getVideoContentsListAll(): List<VideoContents>
    suspend fun getVideoContentsList(id:Int): List<VideoContents>
    suspend fun getVideoContent(id:String): VideoContents
}