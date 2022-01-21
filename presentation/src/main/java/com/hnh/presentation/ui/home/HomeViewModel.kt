package com.hnh.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hnh.domain.entity.Course
import com.hnh.domain.usecase.GetCourseUseCase
import com.hnh.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
) : BaseViewModel() {
    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>> = _courseList

    fun getCourse() {
        getCourseUseCase(viewModelScope) {
            _courseList.value = it
            Log.d("testtest1", it.toString())

        }
//        ,{
        _courseList.value = ArrayList<Course>()
//            when(it){
//                is SocketException -> _fetchState.postValue(FetchState.BAD_INTERNET)
//                is HttpException -> _fetchState.postValue(FetchState.PARSE_ERROR)
//                is UnknownHostException -> _fetchState.postValue(FetchState.WRONG_CONNECTION)
//                else -> _fetchState.postValue(FetchState.FAIL)
//            }
//        })
    }
}