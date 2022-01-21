package com.hnh.domain.usecase

import android.util.Log
import com.hnh.domain.entity.Course
import com.hnh.domain.entity.VideoContents
import com.hnh.domain.repository.CourseRepository
import com.hnh.domain.repository.VideoContentsRepository
import kotlinx.coroutines.*

/**
 * Created by hyerim on 2021/09/26...
 */
class GetVideoContentsUseCase (private val videoContentsRepository: VideoContentsRepository) {
    operator fun invoke(
        scope: CoroutineScope,
        onResult: (List<VideoContents>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    videoContentsRepository.getVideoContentsListAll()
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit Video", "${e.message}")
            }
        }
    }

    operator fun invoke(
        id: Int,
        scope: CoroutineScope,
        onResult: (List<VideoContents>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    videoContentsRepository.getVideoContentsList(id)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit Video", "${e.message}")
            }
        }
    }

    operator fun invoke(
        id: String,
        scope: CoroutineScope,
        onResult: (VideoContents) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    videoContentsRepository.getVideoContent(id)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit Video", "${e.message}")
            }


        }
    }


    private val exceptionHandler = CoroutineExceptionHandler{ context, e ->
        Log.d("Retrofit Video", "${e.message}")
    }
}