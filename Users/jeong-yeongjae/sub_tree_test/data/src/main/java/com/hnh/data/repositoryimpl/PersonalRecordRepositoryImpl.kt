package com.hnh.data.repositoryimpl

import com.hnh.data.source.remote.PersonalRecordRemoteSource
import com.hnh.domain.entity.RecordTotal
import com.hnh.domain.repository.PersonalRecordRepository
import javax.inject.Inject

class PersonalRecordRepositoryImpl @Inject constructor(
    private val personalRecordRemoteSource: PersonalRecordRemoteSource
) : PersonalRecordRepository {
    override suspend fun getTotalRecord(id: Long): RecordTotal {
        return personalRecordRemoteSource.getTotalRecord(id)
    }
}