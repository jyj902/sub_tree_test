package com.hnh.domain.usecase

import android.util.Log
import com.hnh.domain.entity.Version
import com.hnh.domain.entity.VideoContents
import com.hnh.domain.repository.VersionRepository
import com.hnh.domain.repository.VideoContentsRepository
import kotlinx.coroutines.*

/**
 * Created by hyerim on 2021/10/08...
 */
class GetVersionUseCase (private val versionRepository: VersionRepository) {
    operator fun invoke(
        scope: CoroutineScope,
        onResult: (Version?) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    versionRepository.getVersion()
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3 // )
                onResult(null)
                Log.e("Retrofit Version", "${e.message}")
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler{ context, e ->
        Log.d("Retrofit Version", "${e.message}")
    }
}