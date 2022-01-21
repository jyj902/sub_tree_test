package com.hnh.data.repositoryimpl

import com.hnh.data.source.CourseRemoteSource
import com.hnh.data.source.VideoContentRemoteSource
import com.hnh.domain.entity.Course
import com.hnh.domain.entity.VideoContents
import com.hnh.domain.repository.CourseRepository
import com.hnh.domain.repository.VideoContentsRepository
import javax.inject.Inject

/**
 * Created by hyerim on 2021/09/26...
 */
class VideoContentRepositoryImpl @Inject constructor(
    private val videoContentRemoteSource: VideoContentRemoteSource
) : VideoContentsRepository {
    override suspend fun getVideoContentsListAll(): List<VideoContents> {
        return videoContentRemoteSource.getVideoContentsListAll()
    }

    override suspend fun getVideoContentsList(id: Int): List<VideoContents> {
        return videoContentRemoteSource.getVideoContentsList(id)
    }

    override suspend fun getVideoContent(id: String): VideoContents {
        return videoContentRemoteSource.getVideo(id)
    }
}