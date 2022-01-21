package com.hnh.presentation.ui.home.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hnh.domain.entity.RecordTotal
import com.hnh.domain.usecase.GetRecordTotalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getRecordTotalUseCase: GetRecordTotalUseCase
) : ViewModel() {
    private val _totalRecord = MutableLiveData<RecordTotal>()
    val totalRecord : LiveData<RecordTotal> = _totalRecord

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun getTotalRecord(id : Long){
        getRecordTotalUseCase(id, viewModelScope){
            _totalRecord.value = it
        }
    }
}