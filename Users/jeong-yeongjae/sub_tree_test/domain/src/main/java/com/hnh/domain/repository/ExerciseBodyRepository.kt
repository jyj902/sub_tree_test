package com.hnh.domain.repository

import com.hnh.domain.entity.Exercise
import com.hnh.domain.entity.ExerciseBody

interface ExerciseBodyRepository {
    suspend fun getExerciseBodyList(): List<ExerciseBody>
    suspend fun getExerciseBody(id: Long): ExerciseBody
    suspend fun getExerciseList(id: Long): List<Exercise>
}