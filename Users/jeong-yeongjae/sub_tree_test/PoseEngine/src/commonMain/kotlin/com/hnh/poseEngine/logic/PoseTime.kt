package com.hnh.poseengine.logic

import com.hnh.poseengine.OSAL
import com.hnh.poseengine.logic.EXERCISE.SPEED_BAD


class PoseTime(val osal: OSAL, val exerciseGlobal: ExerciseGlobal) {
    //0.1sec 단위 측정한 동작(up, down)
    private var milsPoseState = ""
    //0.1sec 단위 측정한 동작(up, down)의 전상태 기록
    private var milsPreviousPoseState = ""
    //동작(Up, Down) 시간 측정
    var downTime = osal.elapsedRealtime()
    var upTime = osal.elapsedRealtime()
    //0.1sec 단위 포즈 측정용
    var milsFirst = osal.elapsedRealtime()
    var milsSecond = osal.elapsedRealtime()
    //0.1sec 단위 동작 측정 큐
    var milsPoseQueque : ArrayList<String> = ArrayList<String>()
    //전상태 up, down
    var previousPoseState = exerciseGlobal.poseState

    fun initPoseTime(){
        downTime = osal.elapsedRealtime()
        upTime = osal.elapsedRealtime()
        milsFirst = osal.elapsedRealtime()
        milsSecond = osal.elapsedRealtime()
        milsPoseState = ""
        milsPreviousPoseState = ""

//        Global
        exerciseGlobal.frameTimeIn = osal.elapsedRealtime()
        exerciseGlobal.frameTimeOut = osal.elapsedRealtime()
        exerciseGlobal.restTimeIn = osal.elapsedRealtime()
        exerciseGlobal.restTimeOut = osal.elapsedRealtime()
        exerciseGlobal.restBefore10Flag = true
        exerciseGlobal.restBefore3Flag = true
    }
    fun viewRestTime(infoScreen: PoseInfoScreen, exerciseState: Int){
        if(!exerciseGlobal.isStart && exerciseState == EXERCISE_INFORMATION.STATE_REST) {
            val restTime = Math.roundToInt(((exerciseGlobal.restTimeIn - exerciseGlobal.restTimeOut) / 1000).toFloat())
            if((exerciseGlobal.initRestTime/1000 == (restTime + 10)) && (exerciseGlobal.restBefore10Flag)){
                osal.playSound(EXERCISE.REST_BEFORE_10)
                infoScreen.showRestTime10()
                exerciseGlobal.restBefore10Flag = false
            }
            if((exerciseGlobal.initRestTime/1000 == (restTime + 3)) && (exerciseGlobal.restBefore3Flag)){
                osal.playSound(EXERCISE.REST_BEFORE_3)
                exerciseGlobal.restBefore3Flag = false
            }
            val timeString = "RestTime:$restTime"
            val exercisePose = ExercisePose(0, restTime = restTime)
            infoScreen.userInfoResult(exercisePose)
        }
    }
    fun restTimeCheck() : Boolean{
        exerciseGlobal.restTimeIn = osal.elapsedRealtime()
        return ((exerciseGlobal.restTimeIn - exerciseGlobal.restTimeOut) > exerciseGlobal.initRestTime)
    }
    fun exerciseStateCheck(pose: HHPose): Int{

        val poseUtil = PoseUtil(osal, exerciseGlobal, pose)
        val checkFrame :Boolean = when(exerciseGlobal.initExerciseType){
            EXERCISE_INFORMATION.FULL-> {
                poseUtil.checkFrameFull()
            }
            EXERCISE_INFORMATION.UPPER-> {
                poseUtil.checkFrameUpper()
            }
            EXERCISE_INFORMATION.LOWER-> {
                poseUtil.checkFrameLower()
            }
            else -> {
                poseUtil.checkFrameFull()
            }
        }
        if(checkFrame) exerciseGlobal.frameTimeIn = osal.elapsedRealtime()
        else exerciseGlobal.frameTimeOut = osal.elapsedRealtime()
        if(exerciseGlobal.frameTimeIn - exerciseGlobal.frameTimeOut > 3000) return EXERCISE_INFORMATION.STATE_EXERCISE_START
        else if(exerciseGlobal.frameTimeOut - exerciseGlobal.frameTimeIn > 3000)return EXERCISE_INFORMATION.STATE_EXERCISE_STOP
        else return 2
    }
    //운동자세시간 측정
    fun exersiceTimeCheck(): String{
        //error catch
        if ((milsPreviousPoseState == "up") && (milsPoseState == "down")) {
            //앉을때
            //바로 이전 poseresult 초기화
//            failPoint = 0
            if ((upTime - downTime) < exerciseGlobal.initExerciseUpTime){
                exerciseGlobal.poseSpeed = SPEED_BAD
            }
        } else if ((milsPreviousPoseState == "down") && (milsPoseState == "up")) {
            //일어설때
            if ((downTime - upTime) < exerciseGlobal.initExerciseDownTime){
                exerciseGlobal.poseSpeed = SPEED_BAD
            }
        }
        if (milsPoseState == "down") {
            downTime = osal.elapsedRealtime()
        } else {
            upTime = osal.elapsedRealtime()
        }
        if(upTime - downTime > 5000){
            osal.playSound(EXERCISE.MOVE)
            exerciseGlobal.poseResult = true
            upTime = osal.elapsedRealtime()
            downTime = osal.elapsedRealtime()
        }
        previousPoseState = exerciseGlobal.poseState
        return exerciseGlobal.poseSpeed
    }

    fun poseTime():Pair<String, String>{
        var downCount =0
        var upCount = 0
        milsFirst = osal.elapsedRealtime()
        if(milsFirst - milsSecond > 50){
            milsSecond = osal.elapsedRealtime()
            milsPoseQueque.add(exerciseGlobal.poseState)
        }
        if(milsPoseQueque.size > 2){
            milsPreviousPoseState = milsPoseState
            for(pose in milsPoseQueque){
                if(pose == "down") downCount +=1
                else upCount += 1
            }
            milsPoseQueque.removeFirst()
            milsPoseState = if(downCount >= upCount) "down"
            else "up"
        }
        
        return Pair(milsPoseState, milsPreviousPoseState)
    }
    fun poseTimeFilter(): Boolean {
        return ((downTime - upTime > 200) || (upTime - downTime > 200))
    }
}