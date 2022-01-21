package com.hnh.domain.usecase

import android.util.Log
import com.hnh.domain.entity.Exercise
import com.hnh.domain.entity.ExerciseBody
import com.hnh.domain.repository.ExerciseBodyRepository
import kotlinx.coroutines.*

class GetExerciseBodyUseCase(private val exerciseBodyRepository: ExerciseBodyRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        onResult: (List<ExerciseBody>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = withContext(Dispatchers.IO) {
                    exerciseBodyRepository.getExerciseBodyList()
                }
                onResult(deferred)
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit ExerciseBody", "${e.message}")

            }
        }
    }

    operator fun invoke(
        id: Long,
        scope: CoroutineScope,
        onResult: (ExerciseBody) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    exerciseBodyRepository.getExerciseBody(id)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit ExerciseBody", "${e.message}")
            }
        }
    }

    operator fun invoke(
        id: Long,
        legacy : Boolean, //TODO:방식변형필요
        scope: CoroutineScope,
        onResult: (List<Exercise>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    exerciseBodyRepository.getExerciseList(id)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit ExerciseBody", "${e.message}")
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d("Retrofit ExerciseBody", "${e.message}")
    }
}