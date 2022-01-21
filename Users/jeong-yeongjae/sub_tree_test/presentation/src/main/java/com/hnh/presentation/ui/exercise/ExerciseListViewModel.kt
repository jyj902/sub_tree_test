package com.hnh.presentation.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hnh.domain.entity.Exercise
import com.hnh.domain.entity.ExerciseBody
import com.hnh.domain.usecase.GetExerciseBodyUseCase
import com.hnh.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val getExerciseBodyUseCase: GetExerciseBodyUseCase
) : BaseViewModel() {
    private val _exerciseBodyCourse = MutableLiveData<ExerciseBody>()
    val exerciseBodyCourse: LiveData<ExerciseBody> = _exerciseBodyCourse

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>> = _exerciseList

    fun getExerciseBodyCourse(id:Long) {
        getExerciseBodyUseCase(id, viewModelScope) {
            _exerciseBodyCourse.value = it
        }
    }

    fun getExerciseList(id:Long) {
        getExerciseBodyUseCase(id,true, viewModelScope) {
            _exerciseList.value = it
        }
    }
}