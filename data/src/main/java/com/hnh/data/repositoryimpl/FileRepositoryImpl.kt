package com.hnh.data.repositoryimpl

import com.hnh.data.source.remote.FileRemoteSource
import com.hnh.domain.repository.FileRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class FileRepositoryImpl @Inject constructor(
    private val fileRemoteSource: FileRemoteSource
) : FileRepository {
    override suspend fun postCSVFile(
        file: String,
        successCount: Int,
        failCount: Int,
        runningTime: Long,
        restTime: Long,
        isFreeMode: Boolean
    ) {

        //"multipart/form-data"
        val fileBody = File(file).asRequestBody(File(file).extension.toMediaTypeOrNull())
        val files = MultipartBody.Part.createFormData("skeletonFile", File(file).name, fileBody)


        //파일 외 기타 데이터
        val temp = file.replace(".csv", "").split("_")

        val calorie = 0
        val data = HashMap<String, RequestBody>()
        data["userId"] = temp[1].toRequestBody("application/form-data".toMediaTypeOrNull())
        data["setNum"] = temp[3].toRequestBody("application/form-data".toMediaTypeOrNull())
        data["exerciseName"] = temp[2].toRequestBody("application/form-data".toMediaTypeOrNull())
        data["calorie"] = calorie.toString().toRequestBody("application/form-data".toMediaTypeOrNull())
        data["successCount"] = successCount.toString().toRequestBody("application/form-data".toMediaTypeOrNull())
        data["failCount"] = failCount.toString().toRequestBody("application/form-data".toMediaTypeOrNull())
        data["runningTime"] = runningTime.toString().toRequestBody("application/form-data".toMediaTypeOrNull())
        data["restTime"] = restTime.toString().toRequestBody("application/form-data".toMediaTypeOrNull())
        data["isFreeMode"] = isFreeMode.toString().toRequestBody("application/form-data".toMediaTypeOrNull())

        return fileRemoteSource.postCSVFile(files, data)
    }
}