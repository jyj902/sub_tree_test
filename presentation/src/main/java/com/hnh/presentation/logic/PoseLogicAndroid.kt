package com.hnh.presentation.logic

import android.os.SystemClock
import com.google.mlkit.vision.pose.Pose
import com.hnh.presentation.ui.posedetector.PoseDetectorActivity
import com.hnh.presentation.ui.posedetector.PoseDetectorActivity.Companion.isGraphic
import com.hnh.presentation.util.*
import kotlin.collections.ArrayList

/**
 * Created by hyerim on 2021/09/24...
 */

// CSV 기능과 Skeleton View 기능만 남
class PoseLogicAndroid {
//    var instance = PoseDetectorActivity.getPoseContext()
//
//    var listQueue: ArrayList<MutableMap<String, Boolean>> = ArrayList()
//    //csv landmarks 저장용
//    var csvDataListFloat: ArrayList<Array<Float>> = arrayListOf()
//    //스켈레톤 저장시간 고정용 (0.1sec) 시간
//    var previousSkeletonTime = SystemClock.elapsedRealtime()
    var nowSkeletonTime = SystemClock.elapsedRealtime()
    //GraphicOverlay 추가
    fun updateGraphicOverlay(
        pose: Pose,
        graphicOverlay: GraphicOverlay,
        listQueue: ArrayList<MutableMap<String, Boolean>>,
        exerciseType: Int,
        isStart: Boolean) {
        graphicOverlay.add(
            PoseGraphic(
                graphicOverlay,
                pose,
                isGraphic,
                listQueue,
                exerciseType,
                isStart
            )
        )
    }

//    fun saveSkeletonFloat(pose: Pose, csvTimeTable: Long) {
//        nowSkeletonTime = SystemClock.elapsedRealtime()
//        if ((nowSkeletonTime - previousSkeletonTime) > 100L) {
//            val csvLineData: Array<Float> = Array(103) { 0f }
//            previousSkeletonTime = SystemClock.elapsedRealtime()
//            var i = 2
//            val timeTable = (previousSkeletonTime - csvTimeTable).toFloat()
//            csvLineData[0] = (timeTable * 1000).roundToInt() / 1000f
//            if (poseState == "down") csvLineData[1] = 1f
//            else csvLineData[1] = 0f
//            for (data in (pose.allPoseLandmarks)) {
//                csvLineData[i] = (data.position3D.x * 1000).roundToInt() / 1000f
//                csvLineData[i + 1] = (data.position3D.y * 1000).roundToInt() / 1000f
//                csvLineData[i + 2] = (data.position3D.z * 1000).roundToInt() / 1000f
//                i += 3
//            }
//            val counting = countProcess.getCounting()
//            // 잘한개수 , 총개수 - 잘한개수 추가
//            csvLineData[i] = (counting).toFloat()
//            csvLineData[i + 1] = (repsAfter - counting).toFloat()
//            csvDataListFloat.add(csvLineData)
//        }
//    }

//    fun csvSaver() {
//        //CSV Saver
//        val csvHelper = CsvHelper(instance.filesDir.toString())
//        //현재 운동중인 이름으로 저장
//        val current = LocalDateTime.now() // 형태 : 2007-12-03T10:15:30.245
//        val userUid = PreferenceUtil.preferenceInstance(instance).userUid!!
//        val csvName = "${current}_${userUid}_${setData.first}_${nowSet}.csv"
//
//        csvHelper.writeDataFloat(csvName, csvDataListFloat)
//        infoScreen.postFile(instance.filesDir.toString() + "/$csvName", counting, (repsAfter - counting), 10, 40, false) //시간단위 "1초"
//
//        csvDataListFloat.clear() //세트 종료시마다 초기화
//    }

    companion object {
    }
}