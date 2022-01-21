package com.hnh.data.repositoryimpl

import com.hnh.data.entity.CourseRes
import com.hnh.data.source.CourseRemoteSource
import com.hnh.domain.entity.Course
import com.hnh.domain.repository.CourseRepository
import javax.inject.Inject

/**
 * Created by hyerim on 9/16/21...
 */
class CourseRepositoryImpl @Inject constructor(
    private val courseRemoteSource: CourseRemoteSource
) : CourseRepository {
    override suspend fun getCourseList(): List<Course> {
        return courseRemoteSource.getCourseList()
    }

    override suspend fun getCourse(id: Int): Course {
        return courseRemoteSource.getCourse(id)
    }
}