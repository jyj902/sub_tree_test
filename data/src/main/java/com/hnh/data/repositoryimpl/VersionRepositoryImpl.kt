package com.hnh.data.repositoryimpl

import com.hnh.data.source.VersionRemoteSource
import com.hnh.data.source.VideoContentRemoteSource
import com.hnh.domain.entity.Version
import com.hnh.domain.entity.VideoContents
import com.hnh.domain.repository.VersionRepository
import com.hnh.domain.repository.VideoContentsRepository
import javax.inject.Inject

/**
 * Created by hyerim on 2021/10/08...
 */
class VersionRepositoryImpl @Inject constructor(
    private val versionRemoteSource: VersionRemoteSource
) : VersionRepository {
    override suspend fun getVersion(): Version {
        return versionRemoteSource.getVersion()
    }
}