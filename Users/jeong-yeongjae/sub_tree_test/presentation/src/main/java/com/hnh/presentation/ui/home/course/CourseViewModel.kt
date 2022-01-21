package com.hnh.presentation.ui.home.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hnh.domain.entity.Course
import com.hnh.domain.entity.ExerciseBody
import com.hnh.domain.usecase.GetCourseUseCase
import com.hnh.domain.usecase.GetExerciseBodyUseCase
import com.hnh.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by hyerim on 2021/09/24...
 */
@HiltViewModel
class CourseViewModel @Inject constructor(
    private val getExerciseBodyUseCase: GetExerciseBodyUseCase
) : BaseViewModel() {
    private val _exerciseBodyCourse = MutableLiveData<List<ExerciseBody>>()
    val exerciseBodyCourse: LiveData<List<ExerciseBody>> = _exerciseBodyCourse

    fun getExerciseBodyCourseList() {
        getExerciseBodyUseCase(viewModelScope) {
            _exerciseBodyCourse.value = it
        }
    }
}