package com.hnh.domain.repository

import com.hnh.domain.entity.Course

/**
 * Created by hyerim on 9/15/21...
 */
interface CourseRepository {
    suspend fun getCourseList(): List<Course>
    suspend fun getCourse(id: Int): Course
}