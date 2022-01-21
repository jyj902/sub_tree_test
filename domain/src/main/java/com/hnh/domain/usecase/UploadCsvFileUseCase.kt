package com.hnh.domain.usecase

import android.util.Log
import com.hnh.domain.repository.FileRepository
import kotlinx.coroutines.*


class UploadCsvFileUseCase(private val fileRepository: FileRepository){

    operator fun invoke(
        file: String,
        successCount: Int,
        failCount: Int,
        runningTime: Long,
        restTime: Long,
        isFreeMode: Boolean,
        scope: CoroutineScope,
        onResult: () -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    fileRepository.postCSVFile(file, successCount, failCount, runningTime, restTime, isFreeMode)
                }
                onResult()
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit File", "${e.message}")
            }


        }
    }

    private val exceptionHandler = CoroutineExceptionHandler{ _, e ->
        Log.d("Retrofit File", "${e.message}")
    }
}