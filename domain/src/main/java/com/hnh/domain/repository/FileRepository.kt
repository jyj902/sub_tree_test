package com.hnh.domain.repository

interface FileRepository {
    suspend fun postCSVFile(file : String, successCount: Int, failCount: Int, runningTime: Long, restTime: Long, isFreeMode: Boolean)
}