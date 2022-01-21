package com.hnh.presentation.logic

import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import com.hnh.poseengine.logic.*
import com.hnh.presentation.logic.classification.PoseClassifierProcessor
import com.hnh.presentation.logic.classification.PoseClassifierProcessor.Companion.poseState
import com.hnh.presentation.logic.classification.PoseClassifierProcessor.Companion.repsAfter
import com.hnh.presentation.logic.classification.PoseClassifierProcessor.Companion.repsBefore
import com.hnh.presentation.ui.posedetector.PoseDetectorActivity.Companion.isCameraFront
import com.hnh.presentation.ui.posedetector.PoseDetectViewModel
import com.hnh.presentation.ui.posedetector.PoseDetectorActivity
import com.hnh.presentation.util.GraphicOverlay

/**
 * Created by hyerim on 2021/09/24...
 */
class PoseImageAnalyzer(
    private val poseLogicAndroid: PoseLogicAndroid,
    private val poseEngine: PoseLogic,
    private val exerciseGlobal: ExerciseGlobal,
    private val exercise: String,
    private val graphicOverlay: GraphicOverlay,
) : ImageAnalysis.Analyzer {
    private var poseDetector: PoseDetector
    private var classifier: PoseClassifierProcessor
    private var context: Context = PoseDetectorActivity.getPoseContext()

    init {
        //ML 분석기 Accurate version
        val options = AccuratePoseDetectorOptions.Builder()
            .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
            .build()
//        val options = PoseDetectorOptions.Builder()
//            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
//            .build();
        poseDetector = PoseDetection.getClient(options)
        classifier =
            PoseClassifierProcessor(context, true, Pair(exercise, exerciseGlobal.initExerciseType))
    }

    companion object {
    }

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            poseDetector.process(image)
                .addOnSuccessListener { results ->
                    //그래픽오버레이 셋팅
                    graphicOverlay.clear()
                    graphicOverlay.setImageSourceInfo(
                        imageProxy.height,
                        imageProxy.width,
                        isCameraFront
                    )
                    poseEngine.getViewRestTime(exerciseGlobal.exerciseState)
                    poseEngine.getRestTimeCheck()
                    //Start PoseView
                    poseLogicAndroid.updateGraphicOverlay(
                        results,
                        graphicOverlay,
                        poseEngine.listQueue,
                        exerciseGlobal.initExerciseType,
                        exerciseGlobal.isStart)

                    //Convert Pose Data
                    try {
                        val convertPose: MutableList<PoseLandmarkPoseEngine> = arrayListOf()
                        for (body in 0..32) {
                            val position3D = PointF3D(
                                results.getPoseLandmark(body).position3D.x,
                                results.getPoseLandmark(body).position3D.y,
                                results.getPoseLandmark(body).position3D.z)
                            val position = PointF(
                                results.getPoseLandmark(body).position.x,
                                results.getPoseLandmark(body).position.y)
                            val inFrameLikelihood = results.getPoseLandmark(body).inFrameLikelihood
                            convertPose.add(PoseLandmarkPoseEngine(body,
                                position3D,
                                position,
                                inFrameLikelihood))
                        }
                        val hhPose = HHPose(convertPose)
                        //classifier
                        classifier.getPoseResult(results)
                        exerciseGlobal.poseState = poseState
                        exerciseGlobal.repsBefore = repsBefore
                        exerciseGlobal.repsAfter = repsAfter
                        //run PoseEngine
                        poseEngine.run(hhPose, exercise)
                    } catch (e: Exception) {
                        Log.d("PoseImageAnalyzer", e.localizedMessage)
                    }
                }
                .addOnFailureListener { e ->
//                    infoScreen.testInfoResult("{${e.toString()}")
                }.addOnCompleteListener {
                    mediaImage.close()
                    imageProxy.close()
                }
        }
    }
}
