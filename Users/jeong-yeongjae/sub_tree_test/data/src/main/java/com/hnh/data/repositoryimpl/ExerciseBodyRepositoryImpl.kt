package com.hnh.data.repositoryimpl

import com.hnh.data.source.remote.ExerciseBodyRemoteSource
import com.hnh.domain.entity.Exercise
import com.hnh.domain.entity.ExerciseBody
import com.hnh.domain.repository.ExerciseBodyRepository
import javax.inject.Inject

class ExerciseBodyRepositoryImpl @Inject constructor(
    private val exerciseRemoteSource: ExerciseBodyRemoteSource
) : ExerciseBodyRepository {
    override suspend fun getExerciseBodyList(): List<ExerciseBody> {
        return exerciseRemoteSource.getExerciseBodyList()
    }

    override suspend fun getExerciseBody(id: Long): ExerciseBody {
        return exerciseRemoteSource.getExerciseBody(id)
    }

    override suspend fun getExerciseList(id: Long): List<Exercise> {
        return exerciseRemoteSource.getExerciseList(id)
    }
}