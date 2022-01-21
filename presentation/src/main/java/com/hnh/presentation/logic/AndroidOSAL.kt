package com.hnh.presentation.logic

import android.os.SystemClock
import android.util.Log
import com.hnh.poseengine.OSAL
import com.hnh.presentation.logic.classification.PoseClassifierProcessor
import com.hnh.presentation.ui.posedetector.PoseDetectorActivity
import com.hnh.presentation.util.CsvHelper
import com.hnh.presentation.util.ExerciseSoundUtil
import com.hnh.presentation.util.PreferenceUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime

class AndroidOSAL(): OSAL {

    override fun elapsedRealtime(): Long {
        return SystemClock.elapsedRealtime()
    }

    override fun logD(tag: String, log: String) {
        Log.d(tag, log)
    }

    override fun logE(tag: String, log: String) {
        Log.e(tag, log)
    }


    override fun logV(tag: String, log: String) {
        Log.v(tag, log)
    }

    override fun logW(tag: String, log: String) {
        Log.w(tag, log)
    }

    override fun csvSaver(
        csvDataListFloat: ArrayList<Array<Float>>,
        exerciseName: String,
        nowSet: Int,
        counting: Int,
        repsAfter: Int
    ) {
        //CSV Saver
        val instance = PoseDetectorActivity.getPoseContext()
        val csvHelper = CsvHelper(instance.filesDir.toString())
        //현재 운동중인 이름으로 저장
        val current = LocalDateTime.now() // 형태 : 2007-12-03T10:15:30.245
        val userUid = PreferenceUtil.preferenceInstance(instance).userUid!!
        val csvName = "${current}_${userUid}_${exerciseName}_${nowSet}.csv"

        csvHelper.writeDataFloat(csvName, csvDataListFloat)
        postFile(instance.filesDir.toString() + "/$csvName", counting, (repsAfter - counting), 10, 40, false) //시간단위 "1초"

        csvDataListFloat.clear() //세트 종료시마다 초기화
    }
    override fun postFile(
        file: String,
        successCount: Int,
        failCount: Int,
        runningTime: Long,
        restTime: Long,
        isFreeMode: Boolean
    ) {
    }

    override fun resetRepCounter() {
        PoseClassifierProcessor.resetRepCounter = true
    }

    override fun playCountSound(count: Int, param: String) {

    }

    override fun playOnlyCountSound(count: Int) {
        val context = PoseDetectorActivity.getPoseContext()
        ExerciseSoundUtil.getInstance(context)!!.playOnlyCountSound(count)
    }

    override fun playSound(soundName: String) {
        val context = PoseDetectorActivity.getPoseContext()
        ExerciseSoundUtil.getInstance(context)!!.playSound(soundName)
    }

    override fun loadSampleFileOpener(runModel: String): String {
        val context = PoseDetectorActivity.getPoseContext()
        val reader = BufferedReader(
            InputStreamReader(context.assets.open("RunModel/$runModel.csv"))
        )
        var addLine = ""
        var csvLine = reader.readLine()
        while ( csvLine != null){
            addLine += "$csvLine\n"
            csvLine = reader.readLine()
        }
        return addLine
    }

}