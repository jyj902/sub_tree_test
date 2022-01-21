package com.hnh.data.source.remote

import com.hnh.data.entity.RecordTotalRes
import com.hnh.data.service.PersonalRecordService
import javax.inject.Inject

interface PersonalRecordRemoteSource {
    suspend fun getTotalRecord(id: Long): RecordTotalRes
}

class PersonalRecordRemoteSourceImpl @Inject constructor(
    private val personalRecordService: PersonalRecordService
) : PersonalRecordRemoteSource {
    override suspend fun getTotalRecord(id: Long): RecordTotalRes {
        return personalRecordService.getTotalRecord(id)
    }
}
