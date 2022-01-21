package com.hnh.domain.repository

import com.hnh.domain.entity.RecordTotal

interface PersonalRecordRepository {
    suspend fun getTotalRecord(id: Long): RecordTotal
}