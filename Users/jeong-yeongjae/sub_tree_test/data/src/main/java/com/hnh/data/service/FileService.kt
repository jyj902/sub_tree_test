package com.hnh.data.service;

import com.hnh.data.API_V1
import com.hnh.data.SKELETON_UPLOAD
import com.hnh.data.entity.DefaultResponseRes
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.http.*

/**
 * Created by hyerim on 2021/11/23...
 */
interface FileService {
    @Multipart
    @POST("$API_V1$SKELETON_UPLOAD")
    suspend fun postPoseCSV(
        @Part file: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ): DefaultResponseRes
}
