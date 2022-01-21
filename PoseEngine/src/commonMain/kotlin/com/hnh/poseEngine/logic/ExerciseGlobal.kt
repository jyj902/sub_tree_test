package com.hnh.poseengine.logic

import com.hnh.poseengine.OSAL
import com.hnh.poseengine.logic.EXERCISE_INFORMATION.MODE_SET

class ExerciseGlobal(val osal: OSAL) {
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
    var checkListSound: HashMap<String, Boolean> = HashMap()
    // 코칭 사운드 플레이시 카운트 X 용 Flag
    var poseResult = true
    // 포즈 측정 AI 용 큐 삭제 보류
    var dataAI: ArrayList<String> = ArrayList<String>()

    //PoseTime

    //화면에 사람 포착, 미포착 시작 측정용
    var frameTimeIn = osal.elapsedRealtime()
    var frameTimeOut = osal.elapsedRealtime()
    //휴식 시간 측정용
    var restTimeIn = osal.elapsedRealtime()
    var restTimeOut = osal.elapsedRealtime()

    var csvTimeTable = osal.elapsedRealtime()

    var isRecordingCsv = false

    //휴식 시간 종료 알람 1회 플레이 Flag
    var restBefore10Flag = true
    var restBefore3Flag = true

    //동작 속도 String
    var poseSpeed = ""

    //PoseProcess
    //운동 Default Setting
    var initExerciseType = EXERCISE_INFORMATION.FULL
    var initExerciseUpTime = EXERCISE_INFORMATION.SHOUT_TIME
    var initExerciseDownTime = EXERCISE_INFORMATION.SHOUT_TIME
    var initExerciseReps = EXERCISE_INFORMATION.REPS_DEFAULT
    var initExerciseSet = EXERCISE_INFORMATION.SET_DEFAULT
    var initRestTime = EXERCISE_INFORMATION.DEFAULT_REST_TIME

    //Exercise Mode 세트모드 or 자유 운동 모드 선택
    var exerciseMode = MODE_SET

    var buttonSet = false
    var exerciseName : String = ""
    var exerciseState : Int = EXERCISE_INFORMATION.STATE_EXERCISE_STOP
    var resetReps : Boolean = false

    init {
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
        frameTimeIn = osal.elapsedRealtime()
        frameTimeOut = osal.elapsedRealtime()
        //휴식 시간 측정용
        restTimeIn = osal.elapsedRealtime()
        restTimeOut = osal.elapsedRealtime()
        //휴식 시간 종료 알람 1회 플레이 Flag
        restBefore10Flag = true
        restBefore3Flag = true
        //동작 속도 String
        poseSpeed = ""

        //PoseProcess
        //운동 Default Setting
        initExerciseType = EXERCISE_INFORMATION.FIRST_SET
        initExerciseUpTime = EXERCISE_INFORMATION.SHOUT_TIME
        initExerciseDownTime = EXERCISE_INFORMATION.SHOUT_TIME
        initExerciseReps = EXERCISE_INFORMATION.REPS_DEFAULT
        initExerciseSet = EXERCISE_INFORMATION.SET_DEFAULT

        exerciseState = EXERCISE_INFORMATION.STATE_EXERCISE_STOP

    }
}