package com.hnh.data.source.remote

import com.hnh.data.service.FileService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

interface FileRemoteSource {
    suspend fun postCSVFile(file: MultipartBody.Part, data: HashMap<String, RequestBody>)
}

class FileRemoteSourceImpl @Inject constructor(
    private val fileService: FileService
) : FileRemoteSource {
    override suspend fun postCSVFile(file: MultipartBody.Part, data: HashMap<String, RequestBody>) {
        fileService.postPoseCSV(file, data)
    }
}