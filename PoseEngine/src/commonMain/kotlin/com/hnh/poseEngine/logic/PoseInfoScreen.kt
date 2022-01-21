package com.hnh.poseengine.logic

/**
 * Created by hyerim on 2021/09/24...
 */

interface PoseInfoScreen {
    fun userInfoResult(exercisePose: ExercisePose)
    fun testInfoResult(exercisePose: ExercisePose)

    //    fun getContext() : Context
    fun setIntfo(setData: Pair<String, Boolean>)

    //fun setexercisetype()
    fun setCount(count: Int)


    fun showRestTime10()
    fun finishExercise(isFinish: Boolean)
}