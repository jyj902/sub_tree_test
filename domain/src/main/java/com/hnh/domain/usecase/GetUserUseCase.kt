package com.hnh.domain.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import com.hnh.domain.entity.User
import com.hnh.domain.repository.UserRepository
import kotlinx.coroutines.*

/**
 * Created by hyerim on 2021/10/25...
 */
class GetUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        token: String,
        type: String,
        scope: CoroutineScope,
        onResult: (User?) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    userRepository.getUser(token, type)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                onResult(null)
                Log.e("Retrofit Login", "${e.message}")
            }
        }
    }

    operator fun invoke(
        token: String,
        scope: CoroutineScope,
        onResult: (User?) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    userRepository.getUserToken(token)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                onResult(null)
                Log.e("Retrofit User", "${e.message}")
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler{ context, e ->
        Log.d("Retrofit Login", "${e.message}")
    }
}