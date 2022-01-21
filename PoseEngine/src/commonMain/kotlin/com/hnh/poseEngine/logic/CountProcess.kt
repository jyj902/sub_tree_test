package com.hnh.poseengine.logic

import com.hnh.poseengine.OSAL
import com.hnh.poseengine.logic.EXERCISE.SPEED_BAD
import com.hnh.poseengine.logic.EXERCISE.SPEED_GOOD
import kotlin.collections.ArrayList

class CountProcess(val osal: OSAL, val exerciseGlobal: ExerciseGlobal, infoScreen: PoseInfoScreen) {
    private val poseTime = PoseTime(osal, exerciseGlobal)
    private val info = infoScreen
    //세트 종료후 랩스 Classification Reset Flag

    fun initReps(){
        exerciseGlobal.isStart = false
        exerciseGlobal.restBefore10Flag = true
        exerciseGlobal.restBefore3Flag = true
        exerciseGlobal.nowSet++
        exerciseGlobal.counting = 0
        exerciseGlobal.frameTimeIn = osal.elapsedRealtime()
        exerciseGlobal.frameTimeOut = osal.elapsedRealtime()
        exerciseGlobal.resetReps = true
    }
    fun initSet(){
        exerciseGlobal.nowSet = EXERCISE_INFORMATION.SET_FIRST_INT
        exerciseGlobal.isStart = false
        exerciseGlobal.restBefore10Flag = true
        exerciseGlobal.restBefore3Flag = true
        exerciseGlobal.counting = 0
//        viewtext = "Lunge"
//        setData = Pair(viewtext, false)
//        info.setInfo(setData)
        poseTime.initPoseTime()
    }

    fun stateProcess(): Int{
        val stateSet = exerciseGlobal.nowSet
        val stateReps = exerciseGlobal.counting
        val state = exerciseGlobal.isStart
        if(state) exerciseGlobal.restTimeOut = osal.elapsedRealtime()
        if(stateReps >= exerciseGlobal.initExerciseReps){
            if(stateSet>= exerciseGlobal.initExerciseSet){
//                initSet()
                return EXERCISE_INFORMATION.STATE_EXERCISE_FINISH
            }
            else {
                if(poseTime.restTimeCheck()){
                    initReps()
                    return EXERCISE_INFORMATION.STATE_EXERCISE_START
                }
                else return EXERCISE_INFORMATION.STATE_REST
            }
        } else return EXERCISE_INFORMATION.STATE_EXERCISE_START
    }

    fun getCounting(): Int{
        return exerciseGlobal.counting
    }

    // 틀린동작 카운팅
    fun countProcess(getPoseSpeed : String, listQueue : ArrayList<MutableMap<String, Boolean>>) {
        if ((exerciseGlobal.repsAfter > exerciseGlobal.repsBefore)) {
            var failPoint = 0
            for (list in listQueue) {
                for (key in list.keys) {
                    if (list[key] == false) {
                        failPoint++
                    }
                }
            }

            if (exerciseGlobal.poseResult && ((100 - failPoint) >= 99)){// && (getPoseSpeed == SPEED_GOOD)) {
                exerciseGlobal.counting++
                if(exerciseGlobal.counting<exerciseGlobal.initExerciseReps) {
                    osal.playOnlyCountSound(exerciseGlobal.counting)
                }
            } else if ((100 - failPoint) >= 95 && exerciseGlobal.poseResult){// && (getPoseSpeed == SPEED_GOOD)) {
                exerciseGlobal.counting++
                if(exerciseGlobal.counting<exerciseGlobal.initExerciseReps) {
                    osal.playOnlyCountSound(exerciseGlobal.counting)
                }
            } else if (exerciseGlobal.poseResult && getPoseSpeed == SPEED_BAD) {
                exerciseGlobal.counting++
                if(exerciseGlobal.counting<exerciseGlobal.initExerciseReps) {
                    osal.playCountSound(exerciseGlobal.counting, EXERCISE.FAST)
                }
            } else if (exerciseGlobal.poseResult) {
                exerciseGlobal.counting++
                if(exerciseGlobal.counting<exerciseGlobal.initExerciseReps) {
                    osal.playOnlyCountSound(exerciseGlobal.counting)
                }
            } else {
//                ExerciseSoundUtil.getInstance(instance)!!
//                    .playSound(EXERCISE.RATING_4)
            }
            if((exerciseGlobal.counting==exerciseGlobal.initExerciseReps) && ( exerciseGlobal.nowSet == exerciseGlobal.initExerciseSet)){
//                when(exerciseID){
//                    "Squat" -> ExerciseSoundUtil.getInstance(instance)!!
//                        .playCountSound(counting, EXERCISE.SQUAT_3SET_END)
//                    "SideLateralRaise" -> ExerciseSoundUtil.getInstance(instance)!!
//                    .playCountSound(counting, EXERCISE.SIDE_LATERAL_RAISE_3SET_END)
//                    "ShoulderPress" ->ExerciseSoundUtil.getInstance(instance)!!
//                        .playCountSound(counting, EXERCISE.SHOULDER_PRESS_3SET_END)
//                    "Lunge" -> ExerciseSoundUtil.getInstance(instance)!!
//                        .playCountSound(counting, EXERCISE.LUNGE_3SET_END)
//                    "LatPullDownBack" ->ExerciseSoundUtil.getInstance(instance)!!
//                        .playCountSound(counting, EXERCISE.LAT_PULL_DOWN_3SET_END)
//                    else -> ExerciseSoundUtil.getInstance(instance)!!
//                        .playCountSound(counting, EXERCISE.END)
//                }
            }
            exerciseGlobal.poseResult = true
            exerciseGlobal.checkListSound.clear()
            exerciseGlobal.poseSpeed = SPEED_GOOD
        }
    }
    //TempStart
//    fun tmepFun():Boolean{
//        if(counting >= DEFAULT_REPS){
//            isStart = false
//            resetflag = true
//            counting = 0
//            frameTimeIn = SystemClock.elapsedRealtime()
//            frameTimeOut = SystemClock.elapsedRealtime()
//            return true
//        }
//        else if(counting == 10){
////            TTSUtil.getInstance(MainActivity.instance)?.speakText("힘내세요 10개 남았어요")
//            return false
//        }
//        else if(counting == 15){
////            TTSUtil.getInstance(MainActivity.instance)?.speakText("힘내세요 5개 남았어요")
//            return false
//        }
//        else return false
//    }
    //TempEnd
}