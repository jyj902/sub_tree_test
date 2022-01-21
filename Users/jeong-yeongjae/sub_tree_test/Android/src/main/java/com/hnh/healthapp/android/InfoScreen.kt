package com.hnh.healthapp.android

import com.hnh.healthapp.logic.ExercisePose
import com.hnh.healthapp.logic.PoseInfoScreen

class InfoScreen:PoseInfoScreen {
    override fun finishExercise(isFinish: Boolean) {
        TODO("Not yet implemented")
    }

    override fun increaseHandsup() {
        TODO("Not yet implemented")
    }

    override fun increaseSquat() {
        TODO("Not yet implemented")
    }

    override fun postFile(
        file: String,
        successCount: Int,
        failCount: Int,
        runningTime: Long,
        restTime: Long,
        isFreeMode: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun setCount(count: Int) {
        TODO("Not yet implemented")
    }

    override fun setIntfo(setData: Pair<String, Boolean>) {
        TODO("Not yet implemented")
    }

    override fun showRestTime10() {
        TODO("Not yet implemented")
    }

    override fun testInfoResult(exercisePose: ExercisePose) {
        TODO("Not yet implemented")
    }

    override fun userInfoResult(exercisePose: ExercisePose) {
        TODO("Not yet implemented")
    }
}