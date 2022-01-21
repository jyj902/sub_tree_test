package com.hnh.domain.usecase

import android.util.Log
import com.hnh.domain.entity.RecordTotal
import com.hnh.domain.repository.PersonalRecordRepository
import kotlinx.coroutines.*

class GetRecordTotalUseCase(private val personalRecordRepository: PersonalRecordRepository) {
    operator fun invoke(
        id: Long,
        scope: CoroutineScope,
        onResult: (RecordTotal) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main + exceptionHandler) {
            try {
                val deferred = async(Dispatchers.IO) {
                    personalRecordRepository.getTotalRecord(id)
                }
                onResult(deferred.await())
            } catch (e: Throwable) { // (3)
                Log.e("Retrofit Personal Record Total", "${e.message}")
            }


        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { context, e ->
        Log.d("Retrofit Personal Record Total", "${e.message}")
    }
}