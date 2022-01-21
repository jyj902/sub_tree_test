package com.hnh.data.source.remote

import com.hnh.data.entity.ExerciseBodyRes
import com.hnh.data.entity.ExerciseRes
import com.hnh.data.service.ExerciseBodyService
import javax.inject.Inject

interface ExerciseBodyRemoteSource {
    suspend fun getExerciseBodyList(): List<ExerciseBodyRes>
    suspend fun getExerciseBody(id: Long): ExerciseBodyRes
    suspend fun getExerciseList(id: Long): List<ExerciseRes>
}

class ExerciseBodyRemoteSourceImpl @Inject constructor(
    private val exerciseBodyService: ExerciseBodyService
) : ExerciseBodyRemoteSource {
    override suspend fun getExerciseBodyList(): List<ExerciseBodyRes> {
        return exerciseBodyService.getExerciseBodyList()
    }

    override suspend fun getExerciseBody(id: Long): ExerciseBodyRes {
        return exerciseBodyService.getExerciseBody(id)
    }

    override suspend fun getExerciseList(id: Long): List<ExerciseRes> {
        return exerciseBodyService.getExerciseList(id)
    }
}