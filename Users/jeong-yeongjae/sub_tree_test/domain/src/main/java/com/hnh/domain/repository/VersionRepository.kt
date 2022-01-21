package com.hnh.domain.repository

import com.hnh.domain.entity.Version


/**
 * Created by hyerim on 2021/10/08...
 */
interface VersionRepository {
    suspend fun getVersion(): Version
}