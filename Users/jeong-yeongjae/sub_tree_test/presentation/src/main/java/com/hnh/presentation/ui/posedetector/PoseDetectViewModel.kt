package com.hnh.presentation.ui.posedetector

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hnh.domain.usecase.UploadCsvFileUseCase
import com.hnh.poseengine.logic.ExercisePose
import com.hnh.poseengine.logic.PoseInfoScreen
import com.hnh.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoseDetectViewModel @Inject constructor(
    private val uploadCsvFileUseCase: UploadCsvFileUseCase
) : BaseViewModel(), PoseInfoScreen {
    private val _poseState = MutableStateFlow("")
    var poseState : StateFlow<String> = _poseState

    private val _count = MutableStateFlow(0)
    var count : StateFlow<Int> = _count

    private val _infoResult =  MutableStateFlow(ExercisePose(0))
    var infoResult: StateFlow<ExercisePose> = _infoResult

    private val _infoSet = MutableStateFlow(Pair("info", false))
    var infoSet: StateFlow<Pair<String, Boolean>> = _infoSet

    private val _isFinish = MutableStateFlow(false)
    var isFinish: StateFlow<Boolean> = _isFinish

    private var _isRestTime10 = MutableStateFlow(false)
    val isRestTime10: StateFlow<Boolean> = _isRestTime10
    //TODO: Setting 값 적용 가능
    init {
        viewModelScope.launch {
            _poseState.value = ""
            _count.value = 0
            _infoResult.value = ExercisePose(0)
            _infoSet.value = Pair("info", true)
            _isFinish.value = false
            _isRestTime10.value = false
        }

    }
    fun setPoseState(result:String){
        _poseState.value = result
    }

    fun setcount(result: Int){
        _count.value = result
    }

    override fun finishExercise(result: Boolean) {
        _isFinish.value = result
    }

    override fun setCount(count: Int) {
        _count.value = count
    }

    override fun setIntfo(setData: Pair<String, Boolean>) {
        _infoSet.value = setData
    }

    override fun showRestTime10() {
        _isRestTime10.value = true
    }

    override fun testInfoResult(exercisePose: ExercisePose) {
        Log.d("PoseDetectViewModel", "${exercisePose.exerciseFlag}${exercisePose.allCount}")
        _infoResult.value = exercisePose
    }

    override fun userInfoResult(exercisePose: ExercisePose) {
        Log.d("PoseDetectViewModel", "${exercisePose.exerciseFlag}${exercisePose.allCount}")
        _infoResult.value = exercisePose
    }
}