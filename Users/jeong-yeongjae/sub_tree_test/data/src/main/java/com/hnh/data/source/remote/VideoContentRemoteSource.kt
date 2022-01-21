package com.hnh.data.source

import com.hnh.data.entity.VideoContentsRes
import com.hnh.data.service.VideoContentService
import javax.inject.Inject

/**
 * Created by hyerim on 2021/09/26...
 */
interface VideoContentRemoteSource {
    suspend fun getVideoContentsListAll(): List<VideoContentsRes>
    suspend fun getVideoContentsList(id: Int): List<VideoContentsRes>
    suspend fun getVideo(id: String): VideoContentsRes
}

class VideoContentRemoteSourceImpl @Inject constructor(
    private val videoContentService: VideoContentService
) : VideoContentRemoteSource {
    override suspend fun getVideoContentsListAll(): List<VideoContentsRes> {
        return videoContentService.getVideoContentsListAll()
    }

    override suspend fun getVideoContentsList(id: Int): List<VideoContentsRes> {
        return videoContentService.getVideoContentsList(id)
    }

    override suspend fun getVideo(id: String): VideoContentsRes {
        return videoContentService.getVideoContent(id)
    }

}