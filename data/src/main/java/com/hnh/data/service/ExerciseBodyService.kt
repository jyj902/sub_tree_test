package com.hnh.data.service

import com.hnh.data.*
import com.hnh.data.entity.ExerciseBodyRes
import com.hnh.data.entity.ExerciseRes
import retrofit2.http.GET
import retrofit2.http.Path

interface ExerciseBodyService {
    @GET("$API_V1$COURSE_BODY_LIST")
    suspend fun getExerciseBodyList(): List<ExerciseBodyRes>

    @GET("$API_V1$COURSE_BODY_DETAIL")
    suspend fun getExerciseBody(
        @Path("id") id: Long
    ): ExerciseBodyRes

    @GET("$API_V1$COURSE_BODY_CONTENTS")
    suspend fun getExerciseList(@Path("id")id : Long):List<ExerciseRes>
}