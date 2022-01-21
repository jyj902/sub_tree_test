package com.hnh.domain.usecase

import android.util.Log
import com.hnh.domain.entity.Course
import com.hnh.domain.repository.CourseRepository
import kotlinx.coroutines.*

/**
 * Created by hyerim on 9/15/21...
 */
class GetCourseUseCase(private val courseRepository: CourseRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        onResult: (List<Course>) -> Unit = {}
//        onFailure: (Throwable) -> Unit
//        onSuccess: (List<Course>) -> Unit = {},
//        onFailure: (Throwable) -> Unit
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
//            courseRepository.getCourseList().runCatching(onSuccess).onFailure(onFailure)
            try {
                val deferred = withContext(Dispatchers.IO) {
                    courseRepository.getCourseList()
                }
                onResult(deferred)
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit Course", "${e.message}")

            }
        }
    }

    operator fun invoke(
        id: Int,
        scope: CoroutineScope,
        onResult: (Course) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    courseRepository.getCourse(id)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit Course", "${e.message}")
            }


        }
    }

    private val exceptionHandler = CoroutineExceptionHandler{ context, e ->
        Log.d("Retrofit Course", "${e.message}")
    }
}