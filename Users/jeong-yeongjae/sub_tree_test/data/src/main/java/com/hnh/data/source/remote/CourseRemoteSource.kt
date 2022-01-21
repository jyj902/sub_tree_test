package com.hnh.data.source

import com.hnh.data.entity.CourseRes
import com.hnh.data.service.CourseService
import javax.inject.Inject

/**
 * Created by hyerim on 9/16/21...
 */
interface CourseRemoteSource {
    suspend fun getCourseList(): List<CourseRes>
    suspend fun getCourse(id: Int): CourseRes
}

class CourseRemoteSourceImpl @Inject constructor(
    private val courseService: CourseService
) : CourseRemoteSource {
    override suspend fun getCourseList(): List<CourseRes> {
        return courseService.getCourseList()
    }

    override suspend fun getCourse(id: Int): CourseRes {
        return courseService.getCourse(id)
    }

}