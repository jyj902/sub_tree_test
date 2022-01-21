package com.hnh.data.source

import com.hnh.data.entity.VersionRes
import com.hnh.data.service.VersionService
import javax.inject.Inject

/**
 * Created by hyerim on 2021/10/08...
 */
interface VersionRemoteSource {
    suspend fun getVersion(): VersionRes
}

class VersionRemoteSourceImpl @Inject constructor(
    private val versionService: VersionService
) : VersionRemoteSource {
    override suspend fun getVersion(): VersionRes {
        return versionService.getVersion()
    }
}