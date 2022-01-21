package com.hnh.presentation.util

import android.os.SystemClock
import com.hnh.presentation.util.EXERCISE_INFORMATION.MODE_SET
import java.util.*
import kotlin.collections.LinkedHashMap

class ExerciseGlobal {
    companion object {
        //PoseImageAnalyzer
        //운동중 상태 Flag
        var isStart = false

        //CountProcess
        //현재 세트
        var nowSet = EXERCISE_INFORMATION.SET_FIRST_INT
        var counting = 0

        //PoseClassifierProcessor
        var repsAfter = 0
        var repsBefore = 0
        var poseState = ""

        //PoseLogic
        // 1reps 안에서 중복 사운드 체크
        var checkListSound: HashMap<String, Boolean> = LinkedHashMap()
        // 코칭 사운드 플레이시 카운트 X 용 Flag
        var poseResult = true
        // 포즈 측정 AI 용 큐 삭제 보류
        var dataAI: Queue<String> = LinkedList()

        //PoseTime

        //화면에 사람 포착, 미포착 시작 측정용
        var frameTimeIn = SystemClock.elapsedRealtime()
        var frameTimeOut = SystemClock.elapsedRealtime()
        //휴식 시간 측정용
        var restTimeIn = SystemClock.elapsedRealtime()
        var restTimeOut = SystemClock.elapsedRealtime()

        //휴식 시간 종료 알람 1회 플레이 Flag
        var restBefore10Flag = true
        var restBefore3Flag = true

        //동작 속도 String
        var poseSpeed = ""

        //PoseProcess
        //운동 Default Setting
        var exerciseType = EXERCISE_INFORMATION.FULL
        var exerciseUpTime = EXERCISE_INFORMATION.SHOUT_TIME
        var exerciseDownTime = EXERCISE_INFORMATION.SHOUT_TIME
        var exerciseReps = EXERCISE_INFORMATION.REPS_DEFAULT
        var exerciseSet = EXERCISE_INFORMATION.SET_DEFAULT
        var restTime = EXERCISE_INFORMATION.DEFAULT_REST_TIME

        //Exercise Mode 세트모드 or 자유 운동 모드 선택
        var exerciseMode = MODE_SET

        var exerciseState = EXERCISE_INFORMATION.STATE_EXERCISE_STOP

        fun initGlobalObject(){
            run {
                //PoseImageAnalyzer
                //운동중 상태 Flag
                isStart = false
                counting = 0
                //CountProcess
                //현재 세트
                nowSet = EXERCISE_INFORMATION.SET_FIRST_INT

                //PoseLogic
                // 1reps 안에서 중복 사운드 체크
                checkListSound.clear()
                // 코칭 사운드 플레이시 카운트 X 용 Flag
                poseResult = true
                // 포즈 측정 AI 용 큐 삭제 보류
                dataAI.clear()
                //csv landmarks 저장용

                //PoseTime
                //화면에 사람 포착, 미포착 시작 측정용
                frameTimeIn = SystemClock.elapsedRealtime()
                frameTimeOut = SystemClock.elapsedRealtime()
                //휴식 시간 측정용
                restTimeIn = SystemClock.elapsedRealtime()
                restTimeOut = SystemClock.elapsedRealtime()
                //휴식 시간 종료 알람 1회 플레이 Flag
                restBefore10Flag = true
                restBefore3Flag = true
                //동작 속도 String
                poseSpeed = ""

                //PoseProcess
                //운동 Default Setting
                exerciseType = EXERCISE_INFORMATION.FIRST_SET
                exerciseUpTime = EXERCISE_INFORMATION.SHOUT_TIME
                exerciseDownTime = EXERCISE_INFORMATION.SHOUT_TIME
                exerciseReps = EXERCISE_INFORMATION.REPS_DEFAULT
                exerciseSet = EXERCISE_INFORMATION.SET_DEFAULT

                exerciseState = EXERCISE_INFORMATION.STATE_EXERCISE_STOP
            }
        }
    }

}