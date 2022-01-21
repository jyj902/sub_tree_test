package com.hnh.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hnh.domain.entity.Course
import com.hnh.domain.usecase.GetCourseUseCase
import com.hnh.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by hyerim on 9/16/21...
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase
) : BaseViewModel() {

    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>> = _courseList

    fun getCourse() {
//        getCourseUseCase(viewModelScope) {
//            _courseList.value = it
//        }
    }

}