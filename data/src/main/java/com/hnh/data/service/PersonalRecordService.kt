package com.hnh.data.service

import com.hnh.data.API_V1
import com.hnh.data.PERSONAL_RECORD_TOTAL
import com.hnh.data.entity.RecordTotalRes
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonalRecordService {
        @GET("$API_V1$PERSONAL_RECORD_TOTAL")
        suspend fun getTotalRecord(@Path("userId") id: Long): RecordTotalRes
}