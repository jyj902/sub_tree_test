package com.hnh.poseengine.logic

import com.hnh.poseengine.OSAL

/**
 * Created by hyerim on 2021/09/24...
 */
class PoseLogic(
    val osal: OSAL,
    val exerciseGlobal: ExerciseGlobal,
    val infoScreen: PoseInfoScreen,
) {
//    private var classification: PoseClassifierProcessor? = null
    private var process: PoseProcessInterface? = null
    private var logicTimer = PoseTime(osal, exerciseGlobal)
    private var countProcess = CountProcess(osal, exerciseGlobal, infoScreen)
    var listQueue: ArrayList<MutableMap<String, Boolean>> = ArrayList()

    //csv landmarks 저장용
    var csvDataListFloat: ArrayList<Array<Float>> = arrayListOf()

    //스켈레톤 저장시간 고정용 (0.1sec) 시간
    var previousSkeletonTime = osal.elapsedRealtime()
    var nowSkeletonTime = osal.elapsedRealtime()

    fun run(
        hhPose: HHPose,
        exercise: String,
        ) {
        //운동 시작시 로직 동작 true
        if (exerciseGlobal.isStart) {
            //운동 종료 체크
            if (getExerciseStateCheck(hhPose) == 0) {
                exerciseGlobal.isStart = false
                osal.playSound(EXERCISE.END)
            }
            if ((exerciseGlobal.exerciseMode == EXERCISE_INFORMATION.MODE_SET) ||
                (exerciseGlobal.exerciseMode == EXERCISE_INFORMATION.MODE_SELECT_SET)
            ) {
                exerciseGlobal.exerciseState = getStateProcess()
                when (exerciseGlobal.exerciseState) {
                    EXERCISE_INFORMATION.STATE_EXERCISE_STOP -> {
                        exerciseGlobal.isStart = false
                        osal.playSound(EXERCISE.END)
                    }
                    EXERCISE_INFORMATION.STATE_REST -> {
                        exerciseGlobal.isStart = false
//                        infoScreen.csvSaver(csvDataListFloat, exercise, exerciseGlobal.nowSet, exerciseGlobal.counting, exerciseGlobal.repsAfter)
                        osal.playSound(EXERCISE.REST)
                    }
                    EXERCISE_INFORMATION.STATE_EXERCISE_FINISH -> {
                        exerciseGlobal.isStart = false
//                        infoScreen.csvSaver(csvDataListFloat, exercise, exerciseGlobal.nowSet, exerciseGlobal.counting, exerciseGlobal.repsAfter)
                        infoScreen.finishExercise(true)
                    }
                }
            } else if (exerciseGlobal.exerciseMode == EXERCISE_INFORMATION.MODE_FREE) {
                exerciseGlobal.exerciseState = getStateProcess()
                when (exerciseGlobal.exerciseState) {
                    EXERCISE_INFORMATION.STATE_EXERCISE_STOP -> {
                        exerciseGlobal.isStart = false
                        osal.playSound(EXERCISE.END)
                    }
                }
            }
            //운동 시작시 레코딩타임 업데이트
            if (!exerciseGlobal.isRecordingCsv) {
                exerciseGlobal.isRecordingCsv = true
                exerciseGlobal.csvTimeTable = osal.elapsedRealtime()
            }
            if (exerciseGlobal.isStart) {
                poseDetect(hhPose, exercise)
                saveSkeletonFloat(hhPose, exerciseGlobal.csvTimeTable)
            }
        }
        //운동 시작 체크 (현재 운동 X )
        if (!exerciseGlobal.isStart) {
            //운동 종료시 레코딩타임 업데이트
            if (exerciseGlobal.isRecordingCsv) exerciseGlobal.isRecordingCsv = false
            if (getExerciseStateCheck(hhPose) == 1) {
                if ((exerciseGlobal.exerciseMode == EXERCISE_INFORMATION.MODE_SET) ||
                    (exerciseGlobal.exerciseMode == EXERCISE_INFORMATION.MODE_SELECT_SET)
                ) {
                    exerciseGlobal.exerciseState = getStateProcess()
                    when (exerciseGlobal.exerciseState) {
                        EXERCISE_INFORMATION.STATE_EXERCISE_START -> {
                            exerciseGlobal.isStart = true
                            when (exerciseGlobal.nowSet) {
                                EXERCISE_INFORMATION.SET_FIRST_INT ->
                                    osal.playSound(EXERCISE.SET_FIRST)
                                EXERCISE_INFORMATION.SET_SECOND_INT ->
                                    osal.playSound(EXERCISE.SET_SECOND)
                                EXERCISE_INFORMATION.SET_THIRD_INT ->
                                    osal.playSound(EXERCISE.SET_THIRD)
                                else -> osal.playSound(EXERCISE.START)
                            }

                        }
                        EXERCISE_INFORMATION.STATE_REST -> {
                            exerciseGlobal.isStart = false
                        }
//                                STATE_EXERCISE_STOP->{
//                                    isStart = poseLogic.getExerciseStateCheck(results) == 1
//                                    if(isStart) TTSUtil.getInstance(instance)?.speakText("운동을 시작합니다")
//                                }
                    }
                } else if (exerciseGlobal.exerciseMode == EXERCISE_INFORMATION.MODE_FREE) {
                    exerciseGlobal.exerciseState = getStateProcess()
                    if (getExerciseStateCheck(hhPose) == 1) {
                        exerciseGlobal.isStart = true
                        osal.playSound("start")
                    }
                }
            }
        }
    }

    fun poseDetect(pose: HHPose, exercise: String) {
        try {
            var string = ""
            if (process == null) {
                process = selectExercise(exercise)
            }
//            if ((classification == null)) {
//                classification = process!!.classificationType()
//            }
////            if(logicTimer.poseTimeFilter()){
////                classification!!.getPoseResult(pose)
////            }
//            classification!!.getPoseResult(pose)
            if(exerciseGlobal.resetReps){
                osal.resetRepCounter()
                exerciseGlobal.resetReps = false
            }
            //up, down normalize
            val milsPose = logicTimer.poseTime()
            val poseSpeed = logicTimer.exersiceTimeCheck()         //normlize 후 up down 속도 체크
            val checkList = process!!.checkPose(pose, milsPose)
            listQueue.add(checkList)                                //--> PoseGraphic에서 사용
            if (listQueue.size > 10) listQueue.removeFirst()
            string = process!!.checkPose(pose, true)

            process!!.soundSet(listQueue, exerciseGlobal.checkListSound)      //Pose 동작 오류 측정
            countProcess.countProcess(
                poseSpeed,
                listQueue
            )                   // 위 세가지 모두 측정후 개수 프로세스
            osal.logD("countProcess", "${exerciseGlobal.poseResult}")
            osal.logD("countProcess", "${exerciseGlobal.counting}")
            //AI Tester
//            if(exerciseID == "SquatAI") {
//                smootingAI(poseState)
//                string += "\n$nowState"
//            }
            //Debug String
            string += "${exerciseGlobal.poseState}\n"
            //Score String

            val counting = countProcess.getCounting()
            //Observer
            var failcount = exerciseGlobal.repsAfter - counting
            if (failcount < 0) failcount = 0

            if (exerciseGlobal.buttonSet) infoScreen.testInfoResult(
                ExercisePose(
                    -1,
                    exerciseGlobal.nowSet,
                    exerciseGlobal.initExerciseReps,
                    exerciseGlobal.repsAfter,
                    counting,
                    failcount,
                    milsPose.first,
                    poseSpeed,
                    -1,
                    string
                )
            )
            else infoScreen.userInfoResult(
                ExercisePose(
                    1,
                    exerciseGlobal.nowSet,
                    exerciseGlobal.initExerciseReps,
                    exerciseGlobal.repsAfter,
                    counting,
                    failcount,
                    milsPose.first,
                    poseSpeed
                )
            )

        } catch (e: Exception) {
            osal.logD("Error", "$e")
        }
    }

    //운동선택
    private fun selectExercise(exercise: String): PoseProcessInterface {
        return when (exercise) {
            "SquatAI" -> PoseProcess.SquatAI(osal, exerciseGlobal)
            EXERCISE_LIST.DEAD_LIFT -> PoseProcess.DeadLift(osal, exerciseGlobal)
            EXERCISE_LIST.SQUAT -> PoseProcess.Squat(osal, exerciseGlobal)
            EXERCISE_LIST.SIDE_LATERAL_RAISE -> PoseProcess.SideLateralRaise(osal, exerciseGlobal)
            EXERCISE_LIST.SHOULDER_PRESS -> PoseProcess.ShoulderPress(osal, exerciseGlobal)
            EXERCISE_LIST.CABLE_ROW -> PoseProcess.CableLow(osal, exerciseGlobal)
            EXERCISE_LIST.LUNGE -> PoseProcess.Lunge(osal, exerciseGlobal)
            EXERCISE_LIST.LAT_PULL_DOWN -> PoseProcess.LatPullDown(osal, exerciseGlobal)
            EXERCISE_LIST.CABLE_PUSH_DOWN -> PoseProcess.CablePushDown(osal, exerciseGlobal)
            EXERCISE_LIST.STANDING_BARBELL_CURL -> PoseProcess.StandingBarbellCurl(osal,
                exerciseGlobal)
            else -> PoseProcess.Squat(osal, exerciseGlobal)
        }
    }

    fun selectExerciseType(exercise: String) {
        exerciseGlobal.initExerciseType = when (exercise) {
            EXERCISE_LIST.DEAD_LIFT -> EXERCISE_INFORMATION.FULL
            EXERCISE_LIST.SQUAT -> EXERCISE_INFORMATION.LOWER
            EXERCISE_LIST.SIDE_LATERAL_RAISE -> EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.SHOULDER_PRESS -> EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.CABLE_ROW -> EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.LUNGE -> EXERCISE_INFORMATION.LOWER
            EXERCISE_LIST.LAT_PULL_DOWN -> EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.CABLE_PUSH_DOWN -> EXERCISE_INFORMATION.UPPER
            EXERCISE_LIST.STANDING_BARBELL_CURL -> EXERCISE_INFORMATION.UPPER
            else -> EXERCISE_INFORMATION.FULL
        }
    }

    //GraphicOverlay 추가
    //NEED_PORT---------------------------
    /*
    fun updateGraphicOverlay(pose: Pose, graphicOverlay: GraphicOverlay) {
        graphicOverlay.add(
            PoseGraphic(
                graphicOverlay,
                pose,
                isGraphic,
                listQueue
            )
        )
    }
    */
    //NEED_PORT---------------------------

    fun smootingAI(poseState: String) {

        var nowpose: MutableMap<String, Int> = linkedMapOf()
        var score = 0
        exerciseGlobal.dataAI.add(poseState)
        if (exerciseGlobal.dataAI.size > 10) exerciseGlobal.dataAI.clear()
        for (data in exerciseGlobal.dataAI) {
            if (nowpose.containsKey(data)) {
                nowpose[data] = nowpose[data]!! + 1
            } else {
                nowpose[data] = 1
            }
        }
        for (data in nowpose) {
            if (data.value > score) {
                score = data.value
//                poseState = data.key
            }
        }
        nowpose.clear()
    }

    fun saveSkeletonFloat(pose: HHPose, csvTimeTable: Long) {
        nowSkeletonTime = osal.elapsedRealtime()
        if ((nowSkeletonTime - previousSkeletonTime) > 100L) {
            val csvLineData: Array<Float> = Array(103) { 0f }
            previousSkeletonTime = osal.elapsedRealtime()
            var i = 2
            val timeTable = (previousSkeletonTime - csvTimeTable).toFloat()
            csvLineData[0] = Math.roundToInt(timeTable * 1000) / 1000f
            if (exerciseGlobal.poseState == "down") csvLineData[1] = 1f
            else csvLineData[1] = 0f
            for (data in (pose.allPoseLandmarkPoseEngines)) {
                csvLineData[i] = Math.roundToInt(data.position3D.x * 1000) / 1000f
                csvLineData[i + 1] = Math.roundToInt(data.position3D.y * 1000) / 1000f
                csvLineData[i + 2] = Math.roundToInt(data.position3D.z * 1000) / 1000f
                i += 3
            }
            val counting = countProcess.getCounting()
            // 잘한개수 , 총개수 - 잘한개수 추가
            csvLineData[i] = (counting).toFloat()
            csvLineData[i + 1] = (exerciseGlobal.repsAfter - counting).toFloat()
            csvDataListFloat.add(csvLineData)
        }
    }

    fun csvSaver() {
        //NEED_PORT---------------------------
        /*
        //CSV Saver
        val csvHelper = CsvHelper(instance.filesDir.toString())
        //현재 운동중인 이름으로 저장
        val current = LocalDateTime.now() // 형태 : 2007-12-03T10:15:30.245
        val userUid = PrefUtils.preferenceInstance(instance).userUid!!
        val csvName = "${current}_${userUid}_${setData.first}_${nowSet}.csv"

        csvHelper.writeDataFloat(csvName, csvDataListFloat)
        infoScreen.postFile(instance.filesDir.toString() + "/$csvName", counting, (repsAfter - counting), 10, 40, false) //시간단위 "1초"

        csvDataListFloat.clear() //세트 종료시마다 초기화
         */
        //NEED_PORT---------------------------
    }

    //get Time, State
    fun getStateProcess(): Int {
        return countProcess.stateProcess()
    }

    //get FrameIn FrameOut Time
    fun getExerciseStateCheck(pose: HHPose): Int {
        return logicTimer.exerciseStateCheck(pose)
    }

    fun getViewRestTime(exerciseState: Int) {
        return logicTimer.viewRestTime(infoScreen, exerciseState)
    }

    fun getRestTimeCheck(){
        logicTimer.restTimeCheck()
    }

    companion object {
    }
}