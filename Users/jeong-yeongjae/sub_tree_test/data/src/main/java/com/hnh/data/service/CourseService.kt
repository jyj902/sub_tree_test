package com.hnh.data.service

import com.hnh.data.entity.CourseRes
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by hyerim on 9/16/21...
 */
interface CourseService {
    @GET("course")
    suspend fun getCourseList(): List<CourseRes>

    @GET("course/{id}")
    suspend fun getCourse(
        @Path("id") id: Int
    ): CourseRes
}