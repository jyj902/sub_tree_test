package com.hnh.presentation.ui.exercise.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hnh.domain.entity.VideoContents
import com.hnh.domain.usecase.GetVideoContentsUseCase
import com.hnh.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val getVideoContentsUseCase: GetVideoContentsUseCase
) : BaseViewModel() {
    private val _videoContentList = MutableLiveData<List<VideoContents>>()
    val videoContentList: LiveData<List<VideoContents>> = _videoContentList

    fun getVideoContentsList(id: Int){
        getVideoContentsUseCase(id, viewModelScope) {
            _videoContentList.value = it
        }
    }
}